package domain;
/**
 * 使用接口统一封装消息，方便中间过程对消息的传输，
 * 后期对消息的处理也只需要针对消息类型进行处理
 * */
public interface Request{
	//获得消息类型
	public RequestTypeEnum getRequestType();
	//设置消息类型
	public void setRequestType(RequestTypeEnum requestType);
}
