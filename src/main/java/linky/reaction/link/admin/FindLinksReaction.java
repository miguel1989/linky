package linky.reaction.link.admin;

import linky.command.link.admin.FindLinks;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.dto.LinkBeanSimple;
import linky.dto.PageLinksBeanSimple;
import linky.dto.RestResponsePage;
import linky.infra.Reaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLinksReaction implements Reaction<FindLinks, PageLinksBeanSimple> {
	private static final String SQL_PERCENT = "%";

	private final LinkDao linkDao;

	@Autowired
	public FindLinksReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public PageLinksBeanSimple react(FindLinks command) {
		Pageable pageable = PageRequest.of(command.page(), command.size());
		String search = SQL_PERCENT + StringUtils.defaultIfBlank(StringUtils.trim(command.search()), "") + SQL_PERCENT;
		Page<Link> pageLinks = linkDao.findBySearchLikeIgnoreCase(search, pageable);
		List<LinkBeanSimple> linkBeanSimpleList = pageLinks.getContent().stream()
				.map(LinkBeanSimple::new).collect(Collectors.toList());
		RestResponsePage<LinkBeanSimple> pageLinksSimple = new RestResponsePage<>(linkBeanSimpleList, pageable, pageLinks.getTotalElements());
		return new PageLinksBeanSimple(pageLinksSimple);
	}
}
