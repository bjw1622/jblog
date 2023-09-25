package com.poscodx.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(UserVo userVo) {
		boolean checkJoin = userService.join(userVo);
		if (checkJoin == true) {
			return "/user/joinsuccess";
		}
		return "/user/join";
	}
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
}
