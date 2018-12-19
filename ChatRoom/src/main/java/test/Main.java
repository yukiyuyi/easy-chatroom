package test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.ChatRoom;
import domain.User;
import runnable.SocketServerRunnable;

public class Main {
	public static final ApplicationContext CONTEXT=
			new ClassPathXmlApplicationContext(
				"classpath:spring-context.xml","classpath:spring-mybatis.xml");
	public static HashMap<String,ChatRoom> rooms = new HashMap<String,ChatRoom>();
	public String servername = null;
	public ServerSocket ss;
	public Thread thrListen;
	public static HashMap<String, User> users = new HashMap<>();
	public int port;
	public static Server server;
	public static void main(String[] args) {
		try{
			@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(11111);			
			while(true) {
				Socket socket=serverSocket.accept();
				new Thread(new SocketServerRunnable(socket)).start();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addRooms(ChatRoom chatRoom) {
		rooms.put(chatRoom.getChatroomId(), chatRoom);
	}

}
