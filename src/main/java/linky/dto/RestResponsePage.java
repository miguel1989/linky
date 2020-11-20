package linky.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class RestResponsePage<T> extends PageImpl<T> {
	private static final long serialVersionUID = 3248189030448292002L;

	@JsonIgnore
	private Pageable pageable;

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
		super(new ArrayList<>());
	}

//	public Page<T> pageImpl() {
//		return new PageImpl<>(getContent(), PageRequest.of(getNumber(),
//				getSize(), getSort()), getTotalElements());
//	}

	@Override
	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
}
