package linky.controller.admin;

import linky.command.user.DeleteUser;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.DELETE, value = "/{email:.*}")
	public String delete(@PathVariable(value = "email") String email) {
		new DeleteUser(email).execute(pipedNow);
		return "ok";
	}
}
