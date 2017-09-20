package linky.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class RestResponsePage<T> extends PageImpl<T> {

	private static final long serialVersionUID = 3248189030448292002L;

	public RestResponsePage(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public RestResponsePage(List<T> content) {
		super(content);
	}

	/* PageImpl does not have an empty constructor and this was causing an issue for RestTemplate to cast the Rest API response
	 * back to Page.
	 */
	public RestResponsePage() {
		super(new ArrayList<T>());
	}

} 