package linky.reaction

import linky.command.user.RegisterAdmin
import linky.dao.UserDao
import linky.domain.User
import linky.dto.AuthUserBean
import org.assertj.core.util.Lists
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class RegisterAdminReactionShould extends Specification {

	RegisterAdminReaction registerAdminReaction
	UserDao userDao
	PasswordEncoder passwordEncoder

	void setup() {
		userDao = Mock(UserDao)
		passwordEncoder = new BCryptPasswordEncoder()
		registerAdminReaction = new RegisterAdminReaction(userDao, passwordEncoder)
	}

	def 'correct react'() {
		setup:
		RegisterAdmin registerAdmin = new RegisterAdmin('admin@linky.lv', 'secret', 'batman')

		when:
		AuthUserBean authUserBean = registerAdminReaction.react(registerAdmin)

		then:
		assert authUserBean
		assert authUserBean.id
		assert authUserBean.name == 'batman'
		assert authUserBean.email == 'admin@linky.lv'
		assert authUserBean.roles.size() == 2
		1 * userDao.save(_ as User) >> { args ->
			User user = args[0] as User
			assert user.id()
			assert user.createdAt()
			assert user.name() == 'batman'
			assert user.email() == 'admin@linky.lv'
			assert user.password() != 'secret'
			assert user.roles.size() == 2
			Collection<String> expectedRoles = Lists.newArrayList('ROLE_USER', 'ROLE_ADMIN')
			user.roles.each{
				assert expectedRoles.contains(it.role)
			}
		}
	}
}
