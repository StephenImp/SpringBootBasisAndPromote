package com.cn.service;


import com.cn.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class BookService {
	
	//@Qualifier("bookDao")
	//@Autowired(required=false)
	//@Resource(name="bookDao2")
	@Inject
	private BookDao bookDao;
	
	public void print(){
		System.out.println(bookDao);
	}

	@Override
	public String toString() {
		return "BookService [bookDao=" + bookDao + "]";
	}
	
	
	
	

}
