package linky.converter

import linky.domain.User
import linky.dto.UserBean
import spock.lang.Specification

class UserConverterShould extends Specification {

	UserConverter userConverter
	
	void setup() {
		userConverter = new UserConverter()
	}

	def "correct conversion"() {
		when:
		UserBean userBean = userConverter.convert(new User(
				email: 'test@test.com',
				name: 'Barbara',
				password: 'secret'
		))
		
		then:
		userBean.name == 'Barbara'
		userBean.email == 'test@test.com'
		userBean.id
	}
}
