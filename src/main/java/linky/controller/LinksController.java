package linky.controller;

import com.google.common.collect.Lists;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/links")
public class LinksController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<LinkBean> myLinks() {
		return Lists.newArrayList(); //todo me
	}
}
