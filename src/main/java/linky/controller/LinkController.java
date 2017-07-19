package linky.controller;

import com.google.common.collect.Lists;
import linky.command.CreateLink;
import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class LinkController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET, value="/links")
	public Collection<LinkBean> myLinks() {
		return Lists.newArrayList(); //todo me
	}

	@RequestMapping(method = RequestMethod.POST, value = "/link/create")
	public LinkBean create(@RequestBody CreateLinkBean createLinkBean) {
		return new CreateLink(
				AuthUser.id(), 
				createLinkBean.name, 
				createLinkBean.url).execute(pipedNow);
	}
	
	//update my link
	
	//delete my link
}
