package linky.controller.admin;

import linky.command.user.FindUsersPaged;
import linky.dto.UserBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
public class UsersAdminController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET)
	public Page<UserBean> users(@RequestParam(required = false, value = "page") Integer page,
								@RequestParam(required = false, value = "size") Integer pageSize,
								@RequestParam(required = false, value = "search") String search) {
		if (page == null) {
			page = 0;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		return new FindUsersPaged(page, pageSize, search).execute(pipedNow).userBeans;
	}
}
