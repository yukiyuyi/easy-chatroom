package domain;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResponseCodeEnum code;
	private String message;
	public Response() {
	}
	public Response(ResponseCodeEnum code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public ResponseCodeEnum getCode() {
		return code;
	}
	public void setCode(ResponseCodeEnum code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
