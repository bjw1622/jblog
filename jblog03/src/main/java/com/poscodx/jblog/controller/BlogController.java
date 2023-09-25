package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@GetMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(@PathVariable("id") String blogId, @PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo) {
		return "blog/main";
	}

	@GetMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "blog/admin-basic";
	}
}
