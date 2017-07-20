package linky.controller.admin;

import com.google.common.collect.Lists;
import linky.dto.LinkBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin/links")
public class LinksAdminController {

	@RequestMapping(method = RequestMethod.GET)
	public Collection<LinkBean> links() {
		return Lists.newArrayList(); //todo me
	}
}
