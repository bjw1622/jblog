package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

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

	public List<CategoryVo> categoryInfo(CategoryVo categoryVo) {
		List<CategoryVo> categoryList = sqlSession.selectList("blog.categoryInfo", categoryVo);
		return categoryList;
	}

	public CategoryVo findCategoryName(CategoryVo categoryVo) {
		CategoryVo vo = sqlSession.selectOne("blog.categoryNo", categoryVo);
		return vo;
	}

	public boolean addPost(PostVo postVo) {
		int count = sqlSession.insert("blog.insertPost", postVo);
		return count == 1;
	}

	public List<PostVo> postInfo(CategoryVo categoryVo) {
		List<PostVo> postList = sqlSession.selectList("blog.postInfo", categoryVo);
		return postList;
	}

	public void deleteCategory(Long categoryNo) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setNo(categoryNo);
		sqlSession.delete("blog.deletepostForCategory", categoryVo);
		sqlSession.delete("blog.deleteCategory", categoryVo);
	}

	public BlogVo adminBasicinfo(String blogId) {
		BlogVo blogVo = new BlogVo();
		blogVo.setBlogId(blogId);
		return sqlSession.selectOne("blog.adminInfo", blogVo);
	}

	public void update(BlogVo blogVo) {
		sqlSession.update("blog.update", blogVo);
	}

	public List<PostVo> postAll() {
		List<PostVo> postList = sqlSession.selectList("postAllInfo");
		return postList;
	}

}
