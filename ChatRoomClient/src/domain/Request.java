package domain;
/**
 * ʹ�ýӿ�ͳһ��װ��Ϣ�������м���̶���Ϣ�Ĵ��䣬
 * ���ڶ���Ϣ�Ĵ���Ҳֻ��Ҫ�����Ϣ���ͽ��д���
 * */
public interface Request{
	//�����Ϣ����
	public RequestTypeEnum getRequestType();
	//������Ϣ����
	public void setRequestType(RequestTypeEnum requestType);
}
