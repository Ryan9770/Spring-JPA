package com.spring.practice.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.practice.common.MyUtil;
import com.spring.practice.domain.Board;
import com.spring.practice.service.BoardService;

@Controller
@RequestMapping("/bbs/*")
public class BoardController {
	@Autowired
	private BoardService service;

	@Autowired
	private MyUtil myUtil;

	// method를 생략하면 GET, POST 모두를 처리
	@RequestMapping(value = "/bbs/list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			HttpServletRequest req,
			Model model) throws Exception {

		int size = 10;
		int total_page = 0;
		long dataCount = 0;
		List<Board> list = null;
		String paging = null;
		
		try {
			if (req.getMethod().equalsIgnoreCase("GET")) { // GET 방식인 경우
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			Page<Board> pageBoard = service.pageBoard(condition, keyword, current_page, size);

			total_page = pageBoard.getTotalPages();
			if (current_page > total_page) {
				current_page = total_page;
				pageBoard = service.pageBoard(condition, keyword, current_page, size);
			}
			
			dataCount = pageBoard.getTotalElements();
			
			list = pageBoard.getContent();
			for (Board dto : list) {
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}
				
			String cp = req.getContextPath();
			String query = "";
			String listUrl;
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			listUrl = cp + "/bbs/list";
			if (query.length() != 0) {
				listUrl = listUrl + "?" + query;
			}

			paging = myUtil.paging(current_page, total_page, listUrl);
			
		} catch (Exception e) {
		}

		model.addAttribute("list", list);
		model.addAttribute("page", current_page);
		model.addAttribute("size", size);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("total_page", total_page);

		model.addAttribute("paging", paging);

		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);

		return "bbs/list";
	}

	@GetMapping("write")
	public String writeForm(Model model) throws Exception {
		model.addAttribute("mode", "write");
		return "bbs/write";
	}

	@PostMapping("write")
	public String writeSubmit(Board dto, HttpServletRequest req) throws Exception {
		try {
			dto.setIpAddr(req.getRemoteAddr());
			service.insertBoard(dto);
		} catch (Exception e) {
		}

		return "redirect:/bbs/list";
	}

	@GetMapping("article")
	public String article(@RequestParam long num,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword,
			Model model) throws Exception {

		keyword = URLDecoder.decode(keyword, "utf-8");

		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		// 조회수 증가 및 해당 레코드 가져 오기
		service.updateHitCount(num);

		Board dto = service.readBoard(num);
		if (dto == null)
			return "redirect:/bbs/list?" + query;

		// 스타일로 처리하는 경우 : style="white-space:pre;"
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

		// 이전 글, 다음 글
		Board preReadDto = service.preBoard(condition, keyword, num);
		Board nextReadDto = service.nextBoard(condition, keyword, num);

		model.addAttribute("dto", dto);
		model.addAttribute("preReadDto", preReadDto);
		model.addAttribute("nextReadDto", nextReadDto);

		model.addAttribute("page", page);
		model.addAttribute("query", query);

		return "bbs/article";
	}

	@RequestMapping(value = "delete")
	public String delete(@RequestParam long num,
			@RequestParam String page,
			@RequestParam(defaultValue = "all") String condition,
			@RequestParam(defaultValue = "") String keyword)
			throws Exception {

		keyword = URLDecoder.decode(keyword, "utf-8");
		String query = "page=" + page;
		if (keyword.length() != 0) {
			query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		try {
			// 자료 삭제
			service.deleteBoard(num);
		} catch (Exception e) {
		}

		return "redirect:/bbs/list?" + query;
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateForm(@RequestParam long num,
			@RequestParam String page,
			Model model) throws Exception {

		Board dto = service.readBoard(num);
		if (dto == null) {
			return "redirect:/bbs/list?page=" + page;
		}

		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("dto", dto);

		return "bbs/write";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateSubmit(Board dto, @RequestParam String page) throws Exception {

		try {
			// 수정 하기
			service.updateBoard(dto);
		} catch (Exception e) {
		}

		return "redirect:/bbs/list?page=" + page;
	}

}
