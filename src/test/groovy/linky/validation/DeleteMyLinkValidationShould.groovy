package linky.validation

import linky.command.link.DeleteMyLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.exception.ValidationFailed
import spock.lang.Specification

class DeleteMyLinkValidationShould extends Specification {

    DeleteMyLinkValidation deleteLinkValidation
    LinkDao linkDao

    void setup() {
        linkDao = Mock(LinkDao)
        deleteLinkValidation = new DeleteMyLinkValidation(linkDao)
    }

    def 'empty id'() {
        when:
        deleteLinkValidation.validate(new DeleteMyLink('', ''))

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'Link id is empty'
    }

    def 'empty id with spaces'() {
        when:
        deleteLinkValidation.validate(new DeleteMyLink('    ', ''))

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid UUID string:     '
    }

    def 'not an UUID'() {
        when:
        deleteLinkValidation.validate(new DeleteMyLink('batmanID', ''))

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid UUID string: batmanID'
    }

    def 'link not found'() {
        setup:
        String uuid = UUID.randomUUID().toString()
        linkDao.findById(UUID.fromString(uuid)) >> Optional.empty()

        when:
        deleteLinkValidation.validate(new DeleteMyLink(uuid, ''))

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'Link not found'
    }

    def 'not my link'() {
        setup:
        String uuid = UUID.randomUUID().toString()
        linkDao.findById(UUID.fromString(uuid)) >> Optional.of(new Link('myName', 'myUrl', 'batman'))

        when:
        deleteLinkValidation.validate(new DeleteMyLink(uuid, 'superman'))

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'You are not allowed to delete this link'
    }

    def 'everything is ok'() {
        setup:
        String uuid = UUID.randomUUID().toString()
        linkDao.findById(UUID.fromString(uuid)) >> Optional.of(new Link('myName', 'myUrl', 'batman'))

        when:
        deleteLinkValidation.validate(new DeleteMyLink(uuid, 'batman'))

        then:
        noExceptionThrown()
    }
}
