package linky.reaction;

import linky.command.link.FindLink;
import linky.dao.LinkDao;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		return new LinkBean(linkDao.findOne(UUID.fromString(command.id())));
	}
}
