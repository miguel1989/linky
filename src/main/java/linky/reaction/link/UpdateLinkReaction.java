package linky.reaction.link;

import linky.command.link.CreateLink;
import linky.command.link.UpdateLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateLinkReaction implements Reaction<UpdateLink, LinkBean> {

	private final LinkDao linkDao;

	@Autowired
	public UpdateLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public LinkBean react(UpdateLink command) {
		Link link = linkDao.findById(UUID.fromString(command.linkId())).get();

		link.updateNameAndUrl(command.name(), command.url());
		link.updateSearch(); //todo maybe use @prePersist
		linkDao.save(link);
		return new LinkBean(link);
	}
}
