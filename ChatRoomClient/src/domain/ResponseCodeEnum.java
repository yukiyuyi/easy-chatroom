package domain;

import java.io.Serializable;

public enum ResponseCodeEnum implements Serializable {
	SUCCESS(200,"登陆成功"),FAILURE(300,"登录失败"),
	NOTFOUND(400,"服务器没找到"),ERROR(500,"服务器内部错误");
	private int code;
	private String msString;
	private ResponseCodeEnum(int code, String msString) {
		this.code = code;
		this.msString = msString;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsString() {
		return msString;
	}
	public void setMsString(String msString) {
		this.msString = msString;
	}
}
