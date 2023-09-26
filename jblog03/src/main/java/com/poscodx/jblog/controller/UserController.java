package com.poscodx.jblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;

	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(UserVo userVo) {
		boolean checkJoin = userService.join(userVo);
		// 블로그 생성 추가
		if (checkJoin == true) {
			blogService.createBlog(userVo);
			return "redirect:/user/joinsuccess";
		}
		return "redirect:/user/join";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(UserVo userVo, HttpSession httpSession) {
		UserVo loginVo = userService.login(userVo);
		System.out.println(loginVo);
		if (loginVo != null) {
			httpSession.setAttribute("authUser", loginVo);
		}
		return "redirect:/" + loginVo.getId();
	}

	@GetMapping("/joinsuccess")
	public String joinsuccess() {
		return "/user/joinsuccess";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute("authUser");
		httpSession.invalidate();
		return "redirect:/";
	}

}
