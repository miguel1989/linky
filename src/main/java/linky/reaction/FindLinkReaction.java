package linky.reaction;

import linky.command.link.FindLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindLinkReaction implements Reaction<FindLink, LinkBean> {

	private final LinkDao linkDao;

	@Autowired
	public FindLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public LinkBean react(FindLink command) {
		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		return optLink.map(LinkBean::new).orElseGet(LinkBean::new);
	}
}
