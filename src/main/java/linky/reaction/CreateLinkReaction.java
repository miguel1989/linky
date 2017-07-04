package linky.reaction;

import linky.command.CreateLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateLinkReaction implements Reaction<CreateLink, LinkBean> {

	@Autowired
	private LinkDao linkDao;

	@Override
	public LinkBean react(CreateLink command) {
		Link link = new Link(command.name(), command.url(), command.userId());
		linkDao.save(link);
		return new LinkBean(link);
	}
}