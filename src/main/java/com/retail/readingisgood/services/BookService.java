package com.retail.readingisgood.services;

import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy);

    Book getBookById(long id) throws ResourceNotFoundException;

    Book saveBookToStock(Book book) throws ResourceExistsException;

    Book updateBookStock(long id, long count);

    Book getBookByName(String bookName);

    Book updateBook(Book book, long id) throws ResourceNotFoundException;
}
