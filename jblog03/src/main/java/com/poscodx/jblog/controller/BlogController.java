package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(@PathVariable("id") String blogId, @PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo, Model model) {
		BlogVo blogVo = blogService.blogInfo(blogId);
		List<CategoryVo> categoryList = blogService.categoryInfo(blogId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogVo", blogVo);
		
		// 만약 카테고리를 눌렀을 때
		if(!categoryNo.isEmpty()) {
			System.out.println(categoryNo.get());
			List<>
		}
		return "blog/main";
	}

	@GetMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "blog/admin-basic";
	}

	@GetMapping("/admin/category")
	public String adminCategoryForm(@PathVariable("id") String blogId, Model model) {
		List<CategoryVo> categoryList = blogService.categoryInfo(blogId);
		model.addAttribute("categoryList", categoryList);
		return "blog/admin-category";
	}

	@PostMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, CategoryVo categoryVo) {
		categoryVo.setBlogId(blogId);
		boolean checkAddCategory = blogService.addCategory(categoryVo);
		if (checkAddCategory == true) {
			return "redirect:/" + blogId + "/admin/category";
		}
		return "blog/admin-category";
	}

	@GetMapping("/admin/write")
	public String adminWriteForm(@PathVariable("id") String blogId, Model model) {
		List<CategoryVo> categoryList = blogService.categoryInfo(blogId);
		model.addAttribute("categoryList", categoryList);
		return "blog/admin-write";
	}

	@PostMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId, PostVo postVo,
			@RequestParam("category") String category) {
		// 카테고리 이름으로 카테고리 넘버 가져오고
		CategoryVo categoryVo = blogService.findCategoryNo(category);
		postVo.setCategoryNo(categoryVo.getNo());

		// post에 insert
		boolean checkAddPost = blogService.addPost(postVo);
		if (checkAddPost) {
			return "redirect:/" + blogId;
		}
		return "blog/admin-write";
	}
}
