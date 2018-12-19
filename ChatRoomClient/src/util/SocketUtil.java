package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import domain.Request;
import domain.Response;

public class SocketUtil {
	//发送请求，并等待服务器端响应
	public static Response sendRequest(Socket socket, Request request) throws IOException, ClassNotFoundException {
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(request);
		socket.shutdownOutput();
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Response response = (Response) ois.readObject();
//		socket.shutdownInput();
		return response;
	}
	//发送消息，不等待响应
	public static void sendRequestII(Socket socket, Request request) throws IOException, ClassNotFoundException {
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(request);
		oos.flush();
//		socket.shutdownOutput();
	}
	//接收server的消息
	public static Request getRequest(Socket socket) throws IOException, ClassNotFoundException {
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		if(is==null) { //当且仅当接收到消息时，才会进行后续处理，否则会报空指针
			return null;
		}
		Request request = (Request)ois.readObject();
//		socket.shutdownInput();
		return request;
	}
}
