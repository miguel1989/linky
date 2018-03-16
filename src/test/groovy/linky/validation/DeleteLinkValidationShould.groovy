package linky.validation

import linky.command.link.DeleteLink
import linky.dao.LinkDao
import linky.domain.Link
import linky.exception.ValidationFailed
import spock.lang.Specification

class DeleteLinkValidationShould extends Specification {

    DeleteLinkValidation deleteLinkValidation
    LinkDao linkDao

    void setup() {
        linkDao = Mock(LinkDao)
        deleteLinkValidation = new DeleteLinkValidation(linkDao)
    }

    def 'null command'() {
        when:
        deleteLinkValidation.validate(null)

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'Command can not be null'
    }

    def 'empty id'() {
        when:
        deleteLinkValidation.validate(new DeleteLink(''))

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'Link id is empty'
    }

    def 'empty id with spaces'() {
        when:
        deleteLinkValidation.validate(new DeleteLink('    '))

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid UUID string:     '
    }

    def 'not an UUID'() {
        when:
        deleteLinkValidation.validate(new DeleteLink('batmanID'))

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == 'Invalid UUID string: batmanID'
    }

    def 'link not found'() {
        setup:
        String uuid = UUID.randomUUID().toString()
        linkDao.findById(UUID.fromString(uuid)) >> Optional.empty()

        when:
        deleteLinkValidation.validate(new DeleteLink(uuid))

        then:
        def ex = thrown(ValidationFailed)
        ex.message == 'Link not found'
    }

    def 'everything is ok'() {
        setup:
        String uuid = UUID.randomUUID().toString()
        linkDao.findById(UUID.fromString(uuid)) >> Optional.of(new Link())

        when:
        deleteLinkValidation.validate(new DeleteLink(uuid))

        then:
        noExceptionThrown()
    }
}
