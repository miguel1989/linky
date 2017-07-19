package linky.controller;

import com.google.common.collect.Lists;
import linky.dto.AuthUserBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

	@RequestMapping(method = RequestMethod.GET)
	public Collection<AuthUserBean> all() {
		return Lists.newArrayList();
	}
}
