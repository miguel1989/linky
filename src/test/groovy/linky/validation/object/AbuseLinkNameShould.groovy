package linky.validation.object

import spock.lang.Specification

class AbuseLinkNameShould extends Specification {

	AbuseLinkName abuseLinkName

	void setup() {
		abuseLinkName = new AbuseLinkName()
		abuseLinkName.reservedWords = ['login', 'register']
		abuseLinkName.abuseWords = ['niger']
	}

	def 'is ok'() {
		expect:
		abuseLinkName.isOk(name) == result

		where:
		name         | result
		'xloginx'    | true //reserved words, but no full match
		'loginx'     | true //reserved words, but no full match
		'registerMe' | true //reserved words, but no full match
		'REGISTERme' | true //reserved words, but no full match
		'john'       | true //normal
		'login'      | false //reserved words
		'LOGIN'      | false //reserved words
		'register'   | false //reserved words
		'REGISTER'   | false //reserved words
		'niger'      | false //abuse words
		'NIGER'      | false //abuse words
		'XNIGERX'    | false //abuse words
		'xnigerx'    | false //abuse words
		'snigerzz'   | false //abuse words
	}
}
