package com.estore.service;

import com.estore.common.exception.BookException;
import com.estore.dao.IBookDao;
import com.estore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBookServiceImpl implements IBookService {

    @Autowired
    private IBookDao iBookDao;

    @Override
    public List<Book> listAllBooks() throws BookException {
        List<Book> books = iBookDao.queryAll();
        return books;
    }

    @Override
    public Book findById(Long id) throws BookException {
        Book book = iBookDao.queryById(id);
        return book;
    }
}
