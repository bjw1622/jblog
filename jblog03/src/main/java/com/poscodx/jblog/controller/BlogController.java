package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private ServletContext servletContext;

	@GetMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(HttpServletRequest request, @PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo, @PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		System.out.println("아이디 보여조오옹  : ::::::::" + blogId);
		BlogVo blogVo = blogService.blogInfo(blogId);
		List<CategoryVo> categoryList = blogService.categoryInfo(blogId);

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("blogId",blogId);
		List<PostVo> postList = new ArrayList<>();
		List<PostVo> postAllList = new ArrayList<>();
		// 모든 게시글 불러오기
		postAllList = blogService.postAll();

		// 만약 카테고리를 눌렀을 때
		// 카테고리에 해당하는 게시글들이 들어감
		if (!categoryNo.isEmpty()) {
			postList = blogService.postInfo(categoryNo.get());
			model.addAttribute("postList", postList);
			model.addAttribute("categoryNo", categoryNo.get());
		}

		// 카테고리도 누르고 게시글도 눌렀을 때
		if (!categoryNo.isEmpty() && !postNo.isEmpty()) {
			for (PostVo postVo : postAllList) {
				if (postVo.getNo() == postNo.get().intValue()) {
					model.addAttribute("postInfo", postVo);
				}
			}
		}
		return "blog/main";
	}

	@GetMapping("/admin/basic")
	public String adminBasicForm(@PathVariable("id") String blogId, Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("authUser") == null) {
			return "redirect:/" + blogId;
		}
		BlogVo blogVo = blogService.adminBasicInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin-basic";
	}

	@PostMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, BlogVo blogVo,
			@RequestParam("logo-file") MultipartFile file, @RequestParam("title") String title) {
		String url = fileUploadService.restore(file);
		blogVo.setImage(url);
		blogVo.setTitle(title);
		blogVo.setBlogId(blogId);
		if (url == null) {
			blogVo.setImage("");
		}
		blogService.updateSite(blogVo);
		servletContext.setAttribute("blogVo", blogVo);

		return "redirect:/" + blogId + "/admin/basic";
	}

	@GetMapping("/admin/category")
	public String adminCategoryForm(@PathVariable("id") String blogId, Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("authUser") == null) {
			return "redirect:/" + blogId;
		}
		List<CategoryVo> categoryList = new ArrayList<>();
		categoryList = blogService.categoryInfo(blogId);
		BlogVo blogVo = blogService.blogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
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

	@PostMapping("/admin/category/{categoryNo}")
	public String deleteAdminCategory(@PathVariable("id") String blogId, @PathVariable("categoryNo") Long categoryNo) {
		System.out.println(categoryNo);
		blogService.deleteCategory(categoryNo);
		return "redirect:/" + blogId + "/admin/category";
	}

	@GetMapping("/admin/write")
	public String adminWriteForm(@PathVariable("id") String blogId, Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("authUser") == null) {
			return "redirect:/" + blogId;
		}
		List<CategoryVo> categoryList = blogService.categoryInfo(blogId);
		BlogVo blogVo = blogService.blogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		return "blog/admin-write";
	}

	@PostMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String blogId, PostVo postVo,
			@RequestParam("category") String category) {
		// 카테고리 이름으로 카테고리 넘버 가져오고
		// 만약 블로그끼리 카테고리 이름이 겹친다면?? 그때는 에러가 터짐..
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
