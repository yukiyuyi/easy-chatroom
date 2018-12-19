package domain;

import java.io.Serializable;

public enum RequestTypeEnum implements Serializable {
	//注册，登录,发送消息
	REGISTER,LOGIN,SENDMESSAGE,
	//增加用户、删除用户、广播消息
	ADDCLIENT,DELETECLIENT,BROADCAST,
	//私发消息，踢人
	PRIVATECHAT,KICKOUT,SENDCLIENT,
	//更新客户端列表，
	UPDATEUSERLIST;
}
