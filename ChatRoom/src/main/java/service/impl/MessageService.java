package service.impl;

import java.net.Socket;
import org.springframework.stereotype.Service;
import domain.ChatMessage;
import domain.MessageRequest;
import domain.Request;
import domain.RequestTypeEnum;
import domain.Response;
import domain.User;
import service.ServerService;
import test.Server;

@Service
public class MessageService implements ServerService{
	private static Server server = new Server("server", 10001);

	@Override
	public Response service(Request request,Socket socket) {
		ChatMessage chatMessage = ((MessageRequest)request).getMessage();
		try {
			switch(chatMessage.getCode()) {
				case ADDCLIENT:  //当且仅当该用户不在聊天室时才可以登陆成功
					String username = chatMessage.getMessage();
					if(server.judgeUsername(username)||!server.isExistUser(username)) {
						User user = new User(username, socket);
						server.addUser(user);
					} else {
						socket.close();
					}
					return null;
				case BROADCAST:  //广播消息
					server.broadcast(request);
					return null;
				case DELETECLIENT:  //用户主动退出，删除用户
					LoginServiceImpl.users.remove(chatMessage.getMessage());
					server.deleteUser(chatMessage.getMessage(),RequestTypeEnum.DELETECLIENT);
					return null;
				case PRIVATECHAT:   //私发消息
					chatMessage.setMessage(chatMessage.getMessage());
	                server.sendToOne(chatMessage.getToUsername(),
	                		new MessageRequest(chatMessage,RequestTypeEnum.SENDMESSAGE));
					return null;
				case KICKOUT:  //踢出用户
					LoginServiceImpl.users.remove(chatMessage.getMessage());
					server.deleteUser(chatMessage.getMessage(),RequestTypeEnum.KICKOUT);
					return null;
				case UPDATEUSERLIST:  //更新用户列表
					chatMessage.setMessage(server.updateUserList());
					server.broadcast(new MessageRequest(chatMessage,RequestTypeEnum.SENDMESSAGE));
				default:
					break;
			}		
		} catch (Exception e) {
			
		}
		
		return null;
	}
	

}
