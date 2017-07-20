package linky.reaction;

import linky.command.FindLinksForUser;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBean;
import linky.dto.LinksBean;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLinksForUserReaction implements Reaction<FindLinksForUser, LinksBean> {

	private final LinkDao linkDao;

	@Autowired
	public FindLinksForUserReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public LinksBean react(FindLinksForUser command) {
		List<Link> links = linkDao.findByCreatedBy(command.userId());
		return new LinksBean(
				links.stream().map(LinkBean::new).collect(Collectors.toList())
		);
	}
}
