package service;

import java.net.Socket;

import domain.Request;
import domain.Response;

public interface ServerService {
	Response service(Request request,Socket socket);
}
