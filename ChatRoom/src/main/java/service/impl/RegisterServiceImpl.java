package service.impl;

import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Request;
import domain.Response;
import domain.ResponseCodeEnum;
import domain.User;
import domain.UserRequest;
import mapper.UserMapper;
import service.ServerService;

@Service
public class RegisterServiceImpl implements ServerService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public Response service(Request request,Socket socket) {
		synchronized (userMapper) {
			Response response = new Response();
			User user = ((UserRequest)request).getUser();
			if (userMapper.isExistsUserByUname(user.getUsername())) {
				response.setCode(ResponseCodeEnum.FAILURE);
				response.setMessage("用户名已经存在，不能注册");
			} else {
				user.setIsOnline("N");
				if (userMapper.addUser(user) > 0) {
					response.setCode(ResponseCodeEnum.SUCCESS);
					response.setMessage("用户注册成功");
				} else {
					response.setCode(ResponseCodeEnum.ERROR);
					response.setMessage("服务器发生未知错误");
				}
			}
			return response;
		}
	}

}
