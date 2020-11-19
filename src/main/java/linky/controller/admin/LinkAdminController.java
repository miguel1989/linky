package linky.controller.admin;

import linky.command.link.admin.DeleteAnyLink;
import linky.command.link.admin.FindAnyLink;
import linky.dto.LinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Autowired
    private PipedNow pipedNow;

    @RequestMapping(method = RequestMethod.GET, value = "/{id:.*}")
    public LinkBean link(@PathVariable(value = "id") String id) {
        return pipedNow.execute(new FindAnyLink(id));
    }

    //todo admin update link

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id:.*}")
    public String delete(@PathVariable(value = "id") String id) {
        new DeleteAnyLink(id).execute(pipedNow);
        return "ok";
    }

//	@RequestMapping(method = RequestMethod.GET, value = "/{id:.*}/visits")
//	public Collection<VisitBean> visits(@PathVariable(value = "id") String id) {
//		return Lists.newArrayList(); //todo me
//	}
}
