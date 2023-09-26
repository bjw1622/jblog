package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(@PathVariable("id") String blogId, @PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo, Model model) {
		BlogVo blogVo = blogService.blogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/main";
	}

	@GetMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "blog/admin-basic";
	}

	@GetMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId) {
		return "blog/admin-category";
	}

	@GetMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId) {
		return "blog/admin-write";
	}
}
