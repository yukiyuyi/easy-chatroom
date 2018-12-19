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
	//�������󣬲��ȴ�����������Ӧ
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
	//������Ϣ�����ȴ���Ӧ
	public static void sendRequestII(Socket socket, Request request) throws IOException, ClassNotFoundException {
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(request);
		oos.flush();
//		socket.shutdownOutput();
	}
	//����server����Ϣ
	public static Request getRequest(Socket socket) throws IOException, ClassNotFoundException {
		InputStream is = socket.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		if(is==null) { //���ҽ������յ���Ϣʱ���Ż���к�����������ᱨ��ָ��
			return null;
		}
		Request request = (Request)ois.readObject();
//		socket.shutdownInput();
		return request;
	}
}
