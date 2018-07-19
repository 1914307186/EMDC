package com.estore.dao;


import com.estore.entity.Book;

import java.util.List;

public interface IBookDao  {

	List<Book> queryAll();
	Book queryById(Long id);
}
