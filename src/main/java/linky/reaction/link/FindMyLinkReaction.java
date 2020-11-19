package linky.reaction.link;

import linky.command.link.FindMyLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class FindMyLinkReaction implements Reaction<FindMyLink, LinkBean> {

	private final LinkDao linkDao;

	@Autowired
	public FindMyLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public LinkBean react(FindMyLink command) {
		Optional<Link> optLink = linkDao.findById(UUID.fromString(command.id()));
		return optLink.map(LinkBean::new).orElseGet(LinkBean::new);
	}
}
