package linky.validation.object;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LinkUrl {
	public static final int LINK_URL_SIZE = 1000;

	private String urlStr;

	public LinkUrl(String url) {
		this.urlStr = url;
	}

	public boolean isValid() {
		if (!this.urlStr.startsWith("http") || !this.urlStr.startsWith("ftp")) {
			this.urlStr = "http://" + this.urlStr;
		}
		URL url;
		try {
			url = new URL(this.urlStr);
		} catch (MalformedURLException e) {
			return false;
		}
		try {
			url.toURI();
		} catch (URISyntaxException e) {
			return false;
		}

		return this.urlStr.length() <= LINK_URL_SIZE;
	}
}
