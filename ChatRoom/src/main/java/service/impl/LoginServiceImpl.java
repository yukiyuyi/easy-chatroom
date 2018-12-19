package service.impl;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
public class LoginServiceImpl implements ServerService {
	@Autowired
	private UserMapper userMapper;
	public static List<String> users = new ArrayList<String>();

	@Override
	public Response service(Request request,Socket socket) {
		synchronized (userMapper) {
			Response response = new Response();
			User user = ((UserRequest)request).getUser();
			if(users.contains(user.getUsername())) {
				response.setCode(ResponseCodeEnum.FAILURE);				
				response.setMessage("该用户已被登陆，登录失败");
			} else if (userMapper.isExistUser(user)) {
				users.add(user.getUsername());
				response.setCode(ResponseCodeEnum.SUCCESS);				
				response.setMessage("登录成功");
			} else {
				response.setCode(ResponseCodeEnum.FAILURE);
				if (userMapper.isExistsUserByUname(user.getUsername())) {
					response.setMessage("密码错误");
				} else {
					response.setMessage("用户名不存在");
				}
			}
			return response;
		}
	}
}
