package domain;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<String> usersInRoom = new ArrayList<String>();
	private String chatroomId;
	private String username;
	private Time createTime;
	private int port;
	
	public ChatRoom() {
		
	}	
	public ChatRoom(String chatroomId, String username, Time createTime) {
		super();
		this.chatroomId = chatroomId;
		this.username = username;
		this.createTime = createTime;
	}
	public ChatRoom(String chatroomId, String username) {
		super();
		this.chatroomId = chatroomId;
		this.username = username;
	}

	public String getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Time getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Time createTime) {
		this.createTime = createTime;
	}
	public void addUser(String username) {
		usersInRoom.add(username);
	}
	
	public void deleteUser(String username) {
		usersInRoom.remove(username);
	}
	
	public List<String> getUserList() {
		return usersInRoom;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
