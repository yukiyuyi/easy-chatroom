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
	//接收client请求
	public static Request getRequest(Socket socket) throws IOException, ClassNotFoundException {
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		Request request = (Request) ois.readObject();
//		socket.shutdownInput();
		return request;
	}
	//发送response到client
	public static void sendResponse(Socket socket,
			Response response) throws IOException {		
		OutputStream os=socket.getOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(os);
		oos.writeObject(response);
//		socket.shutdownOutput();
	}
	//发送Request到client
	public static void sendRequest(Socket socket,
			Request request) throws IOException {		
		OutputStream os=socket.getOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(os);
		oos.writeObject(request);
		oos.flush();
//		socket.shutdownOutput();
	}
}
