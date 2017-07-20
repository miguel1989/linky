package linky.controller.admin;

import com.google.common.collect.Lists;
import linky.dto.AuthUserBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin/users")
public class UsersAdminController {

	@RequestMapping(method = RequestMethod.GET)
	public Collection<AuthUserBean> users() {
		return Lists.newArrayList(); //todo me
	}
}
