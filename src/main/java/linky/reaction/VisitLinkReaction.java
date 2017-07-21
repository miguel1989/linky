package linky.reaction;

import linky.command.VisitLink;
import linky.dao.LinkDao;
import linky.domain.Link;
import linky.domain.Visit;
import linky.dto.VisitLinkBean;
import linky.event.NewVisitOccurred;
import linky.infra.DomainEvents;
import linky.infra.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VisitLinkReaction implements Reaction<VisitLink, VisitLinkBean> {

	private final LinkDao linkDao;

	@Autowired
	public VisitLinkReaction(LinkDao linkDao) {
		this.linkDao = linkDao;
	}	
	
	@Override
	public VisitLinkBean react(VisitLink command) {
		Optional<Link> optionalLink = linkDao.findByName(command.name());
		if (!optionalLink.isPresent()) {
			return new VisitLinkBean(VisitLink.NOT_FOUND);
		}
		
		Link link = optionalLink.get();
		Visit visit = link.newVisit(command.ip());

		DomainEvents.ephemeral().publish(new NewVisitOccurred(visit.id(), command.ip()));
		
		return new VisitLinkBean(link.url());
	}
}
