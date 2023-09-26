package com.poscodx.jblog.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}

	public UserVo login(UserVo userVo) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", userVo.getId());
		map.put("password", userVo.getPassword());

		return sqlSession.selectOne("user.findByIdAndPassword", map);
	}
}
