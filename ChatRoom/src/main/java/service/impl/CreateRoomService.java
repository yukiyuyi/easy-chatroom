package service.impl;

import java.net.Socket;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.ChatRoom;
import domain.Request;
import domain.Response;
import domain.ResponseCodeEnum;
import domain.RoomRequest;
import mapper.RoomMapper;
import service.ServerService;
import test.Main;
@Service
public class CreateRoomService implements ServerService{
	@Autowired
	RoomMapper roomMapper;
	public static HashMap<String, ChatRoom> chatrooms = new HashMap<String, ChatRoom>();
	@Override
	public Response service(Request request,Socket socket) {		
		ChatRoom chatRoom = ((RoomRequest)request).getChatRoom();
		Response response = new Response();
		if(roomMapper.isExistsRoomByRoomId(chatRoom.getChatroomId())) {
			response.setCode(ResponseCodeEnum.FAILURE);
			String message = chatRoom.getUsername()+"创建"
						+chatRoom.getChatroomId()+"失败，该房间已存在";
			response.setMessage(message);
		} else {
			roomMapper.addRoom(chatRoom);
			response.setCode(ResponseCodeEnum.SUCCESS);
			chatRoom.addUser(chatRoom.getChatroomId());
			chatrooms.put(chatRoom.getChatroomId(), chatRoom);
			Main.addRooms(chatRoom);
			String message = chatRoom.getUsername()+"创建"+chatRoom.getChatroomId()+"成功";
			response.setMessage(message);
		}
		return response;
	}

}
