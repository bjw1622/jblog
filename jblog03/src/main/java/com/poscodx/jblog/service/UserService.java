package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public boolean join(UserVo userVo) {
		return userRepository.insert(userVo);
	}

	public UserVo login(UserVo userVo) {
		return userRepository.login(userVo);
	}
}
