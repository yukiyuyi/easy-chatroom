package domain;

import java.io.Serializable;

public enum ResponseCodeEnum implements Serializable {
	SUCCESS(200,"操作成功"),FAILURE(300,"操作失败"),
	NOTFOUND(400,"服务没找到"),ERROR(500,"服务器内部错误");
	private int code;
	private String msString;
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
	private ResponseCodeEnum(int code, String msString) {
		this.code = code;
		this.msString = msString;
	}	
}
