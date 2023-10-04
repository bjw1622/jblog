package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
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

	public boolean addCategory(CategoryVo categoryVo) {
		return blogRepository.addCategory(categoryVo);
	}

	public List<CategoryVo> categoryInfo(String blogId) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(blogId);
		List<CategoryVo> categoryList = blogRepository.categoryInfo(categoryVo);
		return categoryList;
	}

	public CategoryVo findCategoryNo(String category) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName(category);
		CategoryVo vo = blogRepository.findCategoryName(categoryVo);
		return vo;
	}

	public boolean addPost(PostVo postVo) {
		boolean addCheckPost = blogRepository.addPost(postVo);
		return addCheckPost;
	}

	public List<PostVo> postInfo(Long categoryNo) {
		CategoryVo vo = new CategoryVo();
		vo.setNo(categoryNo);
		List<PostVo> postList = blogRepository.postInfo(vo);
		return postList;
	}

	public void deleteCategory(Long categoryNo) {
		blogRepository.deleteCategory(categoryNo);
	}

	public BlogVo adminBasicInfo(String blogId) {
		return blogRepository.adminBasicinfo(blogId);
	}

	public void updateSite(BlogVo blogVo) {
		blogRepository.update(blogVo);
	}

}
