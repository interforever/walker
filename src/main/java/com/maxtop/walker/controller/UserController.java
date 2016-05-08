
package com.maxtop.walker.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, @RequestBody Map<String, Object> parameters) {
		String username = (String) parameters.get("username");
		String password = (String) parameters.get("password");
		userService.login(username, password);
		request.getSession().setAttribute("username", username);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request) {
		userService.logout();
		request.getSession().removeAttribute("username");
	}
	
}
