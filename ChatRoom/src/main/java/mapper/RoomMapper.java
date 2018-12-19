package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import domain.ChatRoom;

@Repository
public interface RoomMapper {
	//增加房间
	int addRoom(ChatRoom chatroom);
	int deleteRoombyId(String chatroomId);
	int updateRoomByUser(@Param("chatroomId")String chatroomId,@Param("newUsername")String newUsername);
	List<ChatRoom> queryAll();
	
	boolean isExistsRoomByRoomId(String chatroomId);
	boolean isExistRoom(ChatRoom chatroom);
	
	//查找用户
	ChatRoom queryRoomById(String chatroomId);
	ChatRoom queryRoomByUser(String username);
	
}
