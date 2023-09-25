package com.poscodx.jblog.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.UserVo;

import org.apache.ibatis.session.SqlSession;

@Repository
public class UserRepository {
	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}
}
