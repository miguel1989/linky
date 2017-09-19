package linky.reaction;

import com.google.common.base.Strings;
import linky.command.link.FindLinks;
import linky.dao.LinkDao;
import linky.dao.specification.LinkSpecification;
import linky.domain.Link;
import linky.dto.LinkBeanSimple;
import linky.dto.PageLinksBeanSimple;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindLinksReaction implements Reaction<FindLinks, PageLinksBeanSimple> {

	private final LinkDao linkDao;
	
	private static final String SQL_PERCENT = "%";

	@Autowired
	public FindLinksReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	public PageLinksBeanSimple react(FindLinks command) {
		Pageable pageable = new PageRequest(command.page(), command.size());
		//todo maybe implement case insensitive search
		String nameSearch = command.name();
		String urlSearch = command.url();
		
		if (Strings.isNullOrEmpty(nameSearch) && Strings.isNullOrEmpty(urlSearch)) {
			Page<LinkBeanSimple> pageLinks = new PageImpl<>(new ArrayList<>(), pageable, 0);
			return new PageLinksBeanSimple(pageLinks);
		}
		
		if (!Strings.isNullOrEmpty(nameSearch)) {
			nameSearch = SQL_PERCENT + nameSearch + SQL_PERCENT;
		}
		if (!Strings.isNullOrEmpty(urlSearch)) {
			urlSearch = SQL_PERCENT + urlSearch + SQL_PERCENT;
		}

		Page<Link> pageLinks = linkDao.findAll(LinkSpecification.byNameOrUrl(nameSearch, urlSearch), pageable);
		List<LinkBeanSimple> linkBeanSimpleList = pageLinks.getContent().stream()
				.map(LinkBeanSimple::new).collect(Collectors.toList());
		Page<LinkBeanSimple> pageLinksSimple = new PageImpl<>(linkBeanSimpleList, pageable, pageLinks.getTotalElements());
		return new PageLinksBeanSimple(pageLinksSimple);
	}
}
