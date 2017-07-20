package linky.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

	@Value("#{'${reserved.words}'.split(',')}")
	private List<String> reservedWords;

	@RequestMapping(method = RequestMethod.GET, value = "/{name:.*}")
	public String linkRedirect(@PathVariable(value = "name") String name,
							 HttpServletRequest httpServletRequest,
							 HttpServletResponse httpServletResponse) throws IOException {
		System.out.println("name = " + name);
//		if (StringUtils.isBlank(name)) {
//			httpServletRequest.getRequestDispatcher("home").forward(httpServletRequest, httpServletResponse);
//			return;
//		}
//
//		String lowerName = name.toLowerCase();
//		if (reservedWords.stream().filter(lowerName::equals).count() > 0) {
//			httpServletRequest.getRequestDispatcher(name).forward(httpServletRequest, httpServletResponse);
//			return;
//		}

		System.out.println("Ip = " + getIp(httpServletRequest));
		httpServletResponse.sendRedirect("http://www.google.lv");
		return "not_found";
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
