package service;


import domain.Request;
import service.impl.CreateRoomService;
import service.impl.LoginServiceImpl;
import service.impl.MessageService;
import service.impl.RegisterServiceImpl;
import test.Main;

public class ServiceFactory {
	public static ServerService createService(Request request) {
		switch (request.getRequestType()) {
			case REGISTER:
				return Main.CONTEXT.getBean(RegisterServiceImpl.class);
			case LOGIN:
				return Main.CONTEXT.getBean(LoginServiceImpl.class);
//			case CREATEROOM:
//				return Main.CONTEXT.getBean(CreateRoomService.class);
//			case GETROOMLIST:
//				return null;
			case SENDMESSAGE:
				return Main.CONTEXT.getBean(MessageService.class);
			default:
				break;
		}
		return null;
	}
}
