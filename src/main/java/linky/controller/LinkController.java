package linky.controller;

import linky.command.link.CreateLink;
import linky.command.link.DeleteMyLink;
import linky.command.link.FindMyLink;
import linky.dto.CreateLinkBean;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/link")
public class LinkController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET, value = "/{id:.*}")
	public LinkBean find(@PathVariable(value = "id") String id) {
		return new FindMyLink(id, AuthUser.id()).execute(pipedNow);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public LinkBean create(@RequestBody CreateLinkBean createLinkBean) {
		return new CreateLink(
				AuthUser.id(),
				createLinkBean.name,
				createLinkBean.url).execute(pipedNow);
	}

	//update my link

	//delete my link
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id:.*}")
	public String delete(@PathVariable(value = "id") String id) {
		new DeleteMyLink(id, AuthUser.id()).execute(pipedNow);
		return "ok";
	}
}
