package linky.controller;

import linky.command.CreateLink;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	@Autowired
	PipedNow pipedNow;
	
	@RequestMapping("/test")
	public String index() {
		CreateLink createLink = new CreateLink("a", "b", "c");
		createLink.execute(pipedNow);
		return "I am batman";
	}
}
