package linky.controller.admin;

import linky.command.link.admin.FindLinks;
import linky.dto.LinkBeanSimple;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/links")
public class LinksAdminController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET)
	public Page<LinkBeanSimple> links(@RequestParam(required = false, value = "page") Integer page,
									  @RequestParam(required = false, value = "size") Integer pageSize,
									  @RequestParam(value = "sortBy", required = false) String sortField,
									  @RequestParam(value = "sortOrder", required = false) String sortDirection,
									  @RequestParam(required = false, value = "search") String search) {
		return new FindLinks(page, pageSize, sortField, sortDirection, search)
				.execute(pipedNow)
				.pageLinks;
	}
}
