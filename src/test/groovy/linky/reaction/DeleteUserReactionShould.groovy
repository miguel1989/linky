package linky.reaction

import linky.command.user.DeleteUser
import linky.dao.LinkDao
import linky.dao.UserDao
import linky.domain.Link
import linky.domain.User
import spock.lang.Specification

class DeleteUserReactionShould extends Specification {

	DeleteUserReaction deleteUserReaction
	UserDao userDao
	LinkDao linkDao
	
	void setup() {
		linkDao = Mock(LinkDao)
		userDao = Mock(UserDao)
		deleteUserReaction = new DeleteUserReaction(userDao, linkDao)
	}
	
	def 'non existent user'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.empty()
		
		when:
		deleteUserReaction.react(new DeleteUser('test@linky.lv'))
		
		then:
		0 * linkDao.findByCreatedBy(_) 
		0 * linkDao.delete(_) 
		0 * linkDao.delete(_) 
	}

	def 'existing user'() {
		setup:
		userDao.findByEmail('test@linky.lv') >> Optional.of(new User())
		linkDao.findByCreatedBy(_) >> [new Link()]

		when:
		deleteUserReaction.react(new DeleteUser('test@linky.lv'))
		
		then:
		1 * linkDao.delete(_ as List<Link>)
		1 * userDao.delete(_ as User)
	}
}
