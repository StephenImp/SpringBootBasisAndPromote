package com.cn.controller;

import com.cn.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("demo")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@RequestMapping("query")
	public void query(){
		bookService.print();
	}

}
