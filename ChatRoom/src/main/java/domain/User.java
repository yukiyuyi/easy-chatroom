package domain;

import java.io.Serializable;
import java.net.Socket;

import util.SocketUtil;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String isOnline;
    private Socket socket;
	public User() {		
	}
	public User(String username, Socket socket) {
		super();
		this.username = username;
		this.socket = socket;
	}
	public User(String username,String password) {
		super();
		this.username=username;
		this.password=password;
	}	
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public synchronized boolean send2(Request request) {		//发送消息
        try {
        	SocketUtil.sendRequest(socket, request);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	public synchronized void close() {		//关闭用户的相应服务
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
