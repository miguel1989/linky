package linky.controller;

import linky.command.link.VisitLink;
import linky.dto.VisitLinkBean;
import linky.infra.PipedNow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

	private static final String UNKNOWN = "unknown";

	@Autowired
	private PipedNow pipedNow;

	@RequestMapping(method = RequestMethod.GET, value = "/{name:.*}")
	public String linkRedirect(@PathVariable(value = "name") String name,
							   HttpServletRequest httpServletRequest,
							   HttpServletResponse httpServletResponse) throws IOException {
		String ip = getIp(httpServletRequest);

		VisitLinkBean result = new VisitLink(name, ip).execute(pipedNow);

		if (result.url.equals(VisitLink.NOT_FOUND)) {
			return "not_found";
		}

		httpServletResponse.sendRedirect(result.url);
		return "home";//should not happen
	}

	static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
