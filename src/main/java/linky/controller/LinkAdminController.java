package linky.controller;

import com.google.common.collect.Lists;
import linky.dto.LinkBean;
import linky.dto.VisitBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin")
public class LinkAdminController {

	@RequestMapping(method = RequestMethod.GET, value="/links")
	public Collection<LinkBean> links() {
		return Lists.newArrayList(); //todo me
	}

	@RequestMapping(method = RequestMethod.GET, value = "/link/{id:.*}")
	public LinkBean link(@PathVariable(value = "id") String id) {
		return null; //todo me
	}

	@RequestMapping(method = RequestMethod.GET, value = "/link/{id:.*}/visits")
	public Collection<VisitBean> visits(@PathVariable(value = "id") String id) {
		return Lists.newArrayList(); //todo me
	}
}
