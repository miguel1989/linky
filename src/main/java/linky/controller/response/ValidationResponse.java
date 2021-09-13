package linky.controller.response;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse extends BaseResponse {
	private final List<String> errors;

	public ValidationResponse(String code, String message, List<String> errors, String debugInfo) {
		super(code, message, debugInfo);
		this.errors = errors;
	}
}
