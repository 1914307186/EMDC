package com.estore.service;


import com.estore.common.exception.BookException;
import com.estore.entity.Book;

import java.util.List;

public interface IBookService {
	List<Book> listAllBooks() throws BookException;
	Book findById(Long id) throws BookException;
}
