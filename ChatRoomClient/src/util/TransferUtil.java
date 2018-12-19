package util;

import java.util.ArrayList;
import java.util.List;

import domain.ChatRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wrapper.ChatRoomWrapper;

public class TransferUtil {
	
	public static ObservableList<ChatRoomWrapper> chatroomListToObservableList(List<ChatRoom> chatroomsList) {
		ObservableList<ChatRoomWrapper> chatroomObservableList = FXCollections.observableArrayList();
		for(ChatRoom chatroom:chatroomsList) {
			chatroomObservableList.add(new ChatRoomWrapper(chatroom.getChatroomId(),chatroom.getUsername()));
		}
		return chatroomObservableList;
	}
	public static List<ChatRoom> observableListtoChatroomList(ObservableList<ChatRoomWrapper> chatroomObservableList){
		List<ChatRoom> chatroomList = new ArrayList<ChatRoom>();
		for(ChatRoomWrapper chatRoomWrapper:chatroomObservableList) {
			chatroomList.add(new ChatRoom(chatRoomWrapper.getChatroomId(),chatRoomWrapper.getUsername()));
		}
		
		return chatroomList;
	}

	public static ChatRoomWrapper chatroomToChatroomWrapper(ChatRoom chatRoom) {
		return new ChatRoomWrapper(chatRoom.getChatroomId(),chatRoom.getUsername());
	}
	
	public static ChatRoom chatRoomWrapperToChatroom(ChatRoomWrapper chatRoomWrapper) {
		return new ChatRoom(chatRoomWrapper.getChatroomId(),chatRoomWrapper.getUsername());
	}
	

}
