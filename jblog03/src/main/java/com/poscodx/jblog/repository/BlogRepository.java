package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insert(BlogVo blogVo) {
		int count = sqlSession.insert("blog.insert", blogVo);
		return count == 1;
	}

	public BlogVo blogInfo(String blogId) {
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		BlogVo blogVo = sqlSession.selectOne("blog.blogInfo", map);
		return blogVo;
	}

	public boolean addCategory(CategoryVo categoryVo) {
		int count = sqlSession.insert("blog.insertCategory", categoryVo);
		return count == 1;
	}

}
