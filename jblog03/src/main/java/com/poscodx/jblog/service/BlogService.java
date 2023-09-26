package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;

	public boolean createBlog(UserVo userVo) {
		BlogVo blogVo = new BlogVo();
		blogVo.setTitle(userVo.getName() + "의 블로그에 오신걸 콩그레츄레이션");
		blogVo.setImage("");
		blogVo.setBlogId(userVo.getId());
		return blogRepository.insert(blogVo);
	}

	public BlogVo blogInfo(String blogId) {
		BlogVo blogVo = blogRepository.blogInfo(blogId);
		return blogVo;
	}

}
