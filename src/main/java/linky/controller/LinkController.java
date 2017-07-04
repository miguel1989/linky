package linky.controller;

import linky.command.CreateLink;
import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
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
		CreateLink createLink = new CreateLink("test", createLinkBean.name, createLinkBean.url);
		//Todo get logged in userId
		return createLink.execute(pipedNow);
	}
}
