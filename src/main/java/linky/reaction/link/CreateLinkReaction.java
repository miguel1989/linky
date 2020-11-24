package linky.reaction.link;

import linky.command.link.CreateLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateLinkReaction implements Reaction<CreateLink, LinkBean> {

	private final LinkDao linkDao;

	@Autowired
	public CreateLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public LinkBean react(CreateLink command) {
		Link link = new Link(command.name(), command.url(), command.userId());
		link.updateSearch(); //todo maybe use @prePersist
		linkDao.save(link);
		return new LinkBean(link);
	}
}
