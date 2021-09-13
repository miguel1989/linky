package linky.controller.response;

public class BaseResponse {
	protected final String code;
	protected final String message;
	protected final String debugInfo;

	public BaseResponse(String code, String message, String debugInfo) {
		this.code = code;
		this.message = message;
		this.debugInfo = debugInfo;
	}
}
