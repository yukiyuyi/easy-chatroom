package JavaFxMain;
import java.net.Socket;
import domain.ChatMessage;
import domain.MessageRequest;
import domain.Request;
import domain.RequestTypeEnum;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.SocketUtil;
import util.TimeUtil;
import view.MainViewController;

public class Client {
	private String clientName;
	private Socket socket;
	private MainViewController controller = null;
	private Thread thread;
    
    public Client(String clientName) {
    	this.clientName = clientName;
    }
    public String getClientName() {
		return clientName;
	}
    public void setController(MainViewController controller) {
		this.controller = controller;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public void sendData(ChatMessage chatMessage) {
		if(controller==null) {
			return;
		}
		controller.dealMessage(chatMessage);
	}
	public synchronized boolean sendPacket(Request request) {
		try {
			SocketUtil.sendRequestII(socket, request);
		} catch (Exception e) {
			new Alert(AlertType.ERROR,"消息发送失败").showAndWait();
		}
		return true;
	}
	/**
	 * @param to 发送给谁，如果为空则为广播消息
	 * @param msg  消息内容
	 * @param sendTime 消息发送时间
	 * @return 消息发送成功则返回true，否则为false
	 */
	public boolean sendToServer(String to, String msg,String sendTime) {
		ChatMessage chatMessage;
		if("".equals(to)) {  //广播
			chatMessage = new ChatMessage("10001",clientName,to,
    				msg,sendTime,RequestTypeEnum.BROADCAST);
		} else {  //私聊
			chatMessage = new ChatMessage("10001",clientName,to,
    				msg,sendTime,RequestTypeEnum.PRIVATECHAT);
		}
		MessageRequest messageRequest = new MessageRequest(chatMessage,
				RequestTypeEnum.SENDMESSAGE);
		return sendPacket(messageRequest);
	}
	public boolean kickout(String kickClientname) {
		ChatMessage chatMessage = new ChatMessage("10001",clientName,"",kickClientname,
    			TimeUtil.getTime(),RequestTypeEnum.KICKOUT);
		MessageRequest messageRequest = new MessageRequest(chatMessage,RequestTypeEnum.SENDMESSAGE);
		return sendPacket(messageRequest);
	}
	public boolean update() {
		ChatMessage chatMessage = new ChatMessage("10001",clientName,"","update,update,,",
    			TimeUtil.getTime(),RequestTypeEnum.UPDATEUSERLIST);
		MessageRequest messageRequest = new MessageRequest(chatMessage,
    			RequestTypeEnum.SENDMESSAGE);
		return sendPacket(messageRequest);
	}
	public boolean connect(String host,int port) {
		try {
			socket = new Socket(host, port);
			MessageRequest a = new MessageRequest(new ChatMessage("","","",clientName,
					TimeUtil.getTime(),RequestTypeEnum.ADDCLIENT),RequestTypeEnum.SENDMESSAGE);
			SocketUtil.sendRequestII(socket, a);
			thread = new Thread(new Runnable() {	//开启线程,尝试连接服务器
				public void run() {
					try {
						while (true) {
							MessageRequest messageRequest = (MessageRequest)SocketUtil.getRequest(socket);
							if (messageRequest == null) {
								break;
							}
							ChatMessage message = messageRequest.getMessage();
							sendData(message);
						}
						ChatMessage chatMessage = new ChatMessage("","","",clientName,
                    			TimeUtil.getTime(),RequestTypeEnum.DELETECLIENT);
						sendData(chatMessage);
						socket.close();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		} catch (Exception e) {
			new Alert(AlertType.ERROR,"无法连接至服务器。").showAndWait();
			e.printStackTrace();
			return false;
		}
		return true;	
	}
    public void closeClient() {
    	try {			//关闭客户端
        	ChatMessage chatMessage = new ChatMessage("","","",clientName,
        			TimeUtil.getTime(),RequestTypeEnum.DELETECLIENT);
            SocketUtil.sendRequestII(socket, new MessageRequest(chatMessage,RequestTypeEnum.SENDMESSAGE));
            sendData(chatMessage);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}