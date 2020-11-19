package linky.reaction.link.admin;

import linky.command.link.admin.FindAnyLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindAnyLinkReaction implements Reaction<FindAnyLink, LinkBean> {

    private final LinkDao linkDao;

    @Autowired
    public FindAnyLinkReaction(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    @Override
    public LinkBean react(FindAnyLink command) {
        Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
        return optLink.map(LinkBean::new).orElseGet(LinkBean::new);
    }
}
