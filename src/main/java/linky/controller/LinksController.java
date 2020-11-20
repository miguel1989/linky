package linky.controller;

import linky.command.link.FindLinksForUser;
import linky.dto.LinkBeanSimple;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/links")
public class LinksController {

    @Autowired
    private PipedNow pipedNow;

    @RequestMapping(method = RequestMethod.GET)
    public Page<LinkBeanSimple> myLinks(@RequestParam(required = false, value = "page") Integer page,
                                        @RequestParam(required = false, value = "size") Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        return new FindLinksForUser(AuthUser.id(), page, pageSize).execute(pipedNow).pageLinks;
    }
}
