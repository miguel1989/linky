package linky.reaction.link;

import linky.command.link.FindLinksForUser;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBeanSimple;
import linky.dto.PageLinksBeanSimple;
import linky.dto.RestResponsePage;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLinksForUserReaction implements Reaction<FindLinksForUser, PageLinksBeanSimple> {

	private final LinkDao linkDao;

	@Autowired
	public FindLinksForUserReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public PageLinksBeanSimple react(FindLinksForUser command) {
		Pageable pageable = PageRequest.of(command.page(), command.size(), command.sortDirection(), command.sortField());
		Page<Link> pageLinks = linkDao.findByCreatedBy(command.userId(), pageable);

		List<LinkBeanSimple> linkBeanSimpleList = pageLinks.getContent().stream()
				.map(LinkBeanSimple::new).collect(Collectors.toList());
		RestResponsePage<LinkBeanSimple> pageLinksSimple = new RestResponsePage<>(linkBeanSimpleList, pageable, pageLinks.getTotalElements());
		return new PageLinksBeanSimple(pageLinksSimple);
	}
}
