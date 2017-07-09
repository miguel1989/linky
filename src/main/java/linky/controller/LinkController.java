package linky.controller;

import linky.command.CreateLink;
import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/link")
public class LinkController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public LinkBean createLink(@RequestBody CreateLinkBean createLinkBean) {
		return new CreateLink(
				getLoggedInUserId(), 
				createLinkBean.name, 
				createLinkBean.url).execute(pipedNow);
	}
	
	private String getLoggedInUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null 
				&& auth instanceof UsernamePasswordAuthenticationToken
				&& auth.getPrincipal() != null) {
			return auth.getPrincipal().toString();
		}
		return null;
	}
}
