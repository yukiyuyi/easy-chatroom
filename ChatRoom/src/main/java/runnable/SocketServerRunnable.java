package runnable;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.ChatRoom;
import domain.Request;
import domain.RequestTypeEnum;
import domain.Response;
import domain.ResponseCodeEnum;
import service.ServerService;
import service.ServiceFactory;
import test.Server;
import util.SocketUtil;

public class SocketServerRunnable implements Runnable {
	private Socket socket;
	private List<Socket> clients = new ArrayList<>();
	private static HashMap<String, ChatRoom> rooms = new HashMap<>();
	private static Server server = null;

	public SocketServerRunnable() {
	}
	public void startServer() {
		if(server==null) {
			server = new Server("10001",10001);
			(new Thread(server)).start();
		}
	}

	public SocketServerRunnable(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Response response=new Response();
		try {
//			接收client请求
			Object obj = SocketUtil.getRequest(socket);					
			if(obj instanceof String) {
				System.out.println((String)obj);
				response.setCode(ResponseCodeEnum.NOTFOUND);
				response.setMessage("请求的服务不存在，请核查");
				SocketUtil.sendResponse(socket, response);
			} else {
				Request request = (Request)obj;
				ServerService service=ServiceFactory.createService(request);
				if(service==null) {
					response.setCode(ResponseCodeEnum.NOTFOUND);
					response.setMessage("请求的服务不存在，请核查");
				}else {
					response=service.service(request,socket);
					if(request.getRequestType()==RequestTypeEnum.LOGIN 
							&& response.getCode()==ResponseCodeEnum.SUCCESS) {
						startServer();
						clients.add(socket);
//					} else if(request.getRequestType()==RequestTypeEnum.CREATEROOM
//							&& response.getCode()==ResponseCodeEnum.SUCCESS) {
//						ChatRoom chatRoom = ((RoomRequest)request).getChatRoom();
//						rooms.put(chatRoom.getChatroomId(), chatRoom);
//						(new Thread(new Server(chatRoom.getChatroomId(),chatRoom.getPort()))).start();
					}
				}
				SocketUtil.sendResponse(socket, response);
			}	
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();				
			response.setCode(ResponseCodeEnum.ERROR);
			response.setMessage("服务器内部发生错误");
			try {
				SocketUtil.sendResponse(socket, response);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
