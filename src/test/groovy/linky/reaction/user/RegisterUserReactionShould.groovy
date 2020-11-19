package linky.reaction.user

import linky.command.user.RegisterUser
import linky.dao.UserDao
import linky.domain.User
import linky.dto.AuthUserBean
import linky.reaction.user.RegisterUserReaction
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class RegisterUserReactionShould extends Specification {

	RegisterUserReaction registerUserReaction
	UserDao userDao
	PasswordEncoder passwordEncoder

	void setup() {
		userDao = Mock(UserDao)
		passwordEncoder = new BCryptPasswordEncoder()
		registerUserReaction = new RegisterUserReaction(userDao, passwordEncoder)
	}

	def "correct react"() {
		setup:
		RegisterUser registerUser = new RegisterUser('user@linky.lv', 'secret', 'peter parker')

		when:
		AuthUserBean authUserBean = registerUserReaction.react(registerUser)

		then:
		assert authUserBean
		assert authUserBean.id
		assert authUserBean.name == 'peter parker'
		assert authUserBean.email == 'user@linky.lv'
		assert authUserBean.roles.size() == 1
		1 * userDao.save(_ as User) >> { args ->
			User user = args[0] as User
			assert user.id()
			assert user.createdAt()
			assert user.name() == 'peter parker'
			assert user.email() == 'user@linky.lv'
			assert user.password() != 'secret'
			assert user.roles.size() == 1
			assert user.roles.iterator().next().role == 'ROLE_USER'
		}
	}
}
