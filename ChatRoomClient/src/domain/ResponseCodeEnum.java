package domain;

import java.io.Serializable;

public enum ResponseCodeEnum implements Serializable {
	SUCCESS(200,"��½�ɹ�"),FAILURE(300,"��¼ʧ��"),
	NOTFOUND(400,"������û�ҵ�"),ERROR(500,"�������ڲ�����");
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
