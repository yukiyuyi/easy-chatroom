package mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserMapper {
	//增加用户
	int addUser(User user);
	boolean isExistsUserByUname(String username);
	boolean isExistUser(User user);
	
	//查找用户
	User queryUser(String username);
	List<User> queryAll();
}
