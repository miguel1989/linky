package linky.controller.admin;

import com.google.common.collect.Lists;
import linky.command.link.FindLink;
import linky.dto.LinkBean;
import linky.dto.VisitBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET, value = "/{id:.*}")
	public LinkBean link(@PathVariable(value = "id") String id) {
		return new FindLink(id).execute(pipedNow);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id:.*}/visits")
	public Collection<VisitBean> visits(@PathVariable(value = "id") String id) {
		return Lists.newArrayList(); //todo me
	}
}
