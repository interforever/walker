
package com.maxtop.walker.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maxtop.walker.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${login.username:admin}")
	private String username;
	
	@Value("${login.password:woaijinsheng}")
	private String password;
	
	public void login(String username, String password) {
		if (!this.username.equals(username) || !this.password.equals(password)) throw new RuntimeException("Wrong username or password!");
	}
	
	public void logout() {
	}
	
}
