package test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import service.ServerService;
import service.ServiceFactory;
import domain.ChatMessage;
import domain.MessageRequest;
import domain.Request;
import domain.RequestTypeEnum;
import domain.User;
import util.SocketUtil;
import util.TimeUtil;


public class Server implements Runnable{
	private String servername;
	private ServerSocket ss;
	private Thread thrListen;
	private HashMap<String, User> users = new HashMap<>();
	private int port;
    
	public Server(String servername,int port) {
		this.servername=servername;
		this.port=port;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServername() {
        return servername;
    }
	
	public boolean judgeUsername(String username) {		//判断用户名是否合法
        if (username == null||"".equals(username)) {
            return false;
        }
        return true;
    }
	
	public boolean isExistUser(String username) {
		return users.containsKey(username);
	}

    public synchronized boolean addUser(User user) {		//添加用户
    	//用户名不合法或者已存在，不能加入
    	if (!judgeUsername(user.getUsername())||isExistUser(user.getUsername())) {
    		return false;
    	}
    	users.put(user.getUsername(), user);
    	ChatMessage chatMessage = new ChatMessage("10001","","",user.getUsername(),TimeUtil.getTime(),RequestTypeEnum.ADDCLIENT);
    	MessageRequest messageRequest = new MessageRequest(chatMessage,RequestTypeEnum.SENDMESSAGE);
    	broadcast(messageRequest);
    	return true;
    }

    public synchronized void deleteUser(String username,RequestTypeEnum code) {	//删除用户
    	if (users.containsKey(username)) {
    		User user = users.get(username);
    		user.close();
    		users.remove(username);
    		ChatMessage cms = new ChatMessage("10001","","",username,TimeUtil.getTime(),code);
    		MessageRequest msg = new MessageRequest(cms,RequestTypeEnum.SENDMESSAGE);
    		broadcast(msg);
    	}
	}
	public synchronized boolean broadcast(Request request) {	//广播消息
    	for (User user:users.values()) {		//依次遍历所有用户
    		if (!user.send2(request)) {			//发送失败则删除该用户
    			deleteUser(user.getUsername(),RequestTypeEnum.DELETECLIENT);
    		}
    	}
    	return true;
	}
	public synchronized boolean sendToOne(String username, Request request) {	//私聊
		if (!users.containsKey(username)) {	//私聊对象不在列表
			return false;
		}
		User user = users.get(username);			//私聊对象不在线，则删除
		if (!user.send2(request)) {
			deleteUser(user.getUsername(),RequestTypeEnum.DELETECLIENT);
		}
		return true;
	}

	public synchronized String updateUserList() {		//更新在线列表
		StringBuilder sb = new StringBuilder();
		for (String n : users.keySet()) {		//遍历在线列表
			sb.append(n);
			sb.append(",");
		}
		return sb.toString();
    }
    
    class SocketServerRunnable implements Runnable {
		private Socket socket;
		public SocketServerRunnable() {
			
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
			try {
				while(true) {
					//接收client请求
					Request request = SocketUtil.getRequest(socket);
					if(request==null) {
						break;
					}
					ServerService service=ServiceFactory.createService(request);
					service.service(request,socket);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

    public boolean is_server() {		//判断客户端或服务器
        return true;
    }

    @Override
	public void run() {
    	try {
            ss = new ServerSocket(port);
            thrListen = new Thread(new Runnable() {		//开启新的线程等待其他客户端连接
                public void run() {
                    try {
                        while (true) {
                            Socket s = ss.accept();
                            Thread thr = new Thread(new SocketServerRunnable(s));
                            thr.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thrListen.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
