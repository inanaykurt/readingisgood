package com.retail.readingisgood.services.impl;

import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Book;
import com.retail.readingisgood.repository.BookRepository;
import com.retail.readingisgood.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Book> pagedResult = bookRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Book>();
        }
    }

    @Override
    public Book getBookById(long id) throws ResourceNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent() && book.get().getBookCount() > 0) {
            return book.get();
        } else {
            throw new ResourceNotFoundException("Book Not Found in Stock");
        }
    }

    @Override
    public Book saveBookToStock(Book newBook) throws ResourceExistsException {

        Book stockBook = bookRepository.getBookByName(newBook.getName(), newBook.getAuthor());

        if(stockBook != null){
            throw new ResourceExistsException(stockBook.toString());
        }

        return bookRepository.save(newBook);
    }

    @Override
    public Book updateBookStock(long id, long count) {

        Optional<Book> bookInStock = bookRepository.findById(id);

        // update stocks
        if(bookInStock.isPresent()){
            long newBookCount = bookInStock.get().getBookCount() + count;
            bookRepository.updateStockOfBook(newBookCount,  bookInStock.get().getId());
            bookInStock.get().setBookCount(newBookCount);
            return bookInStock.get();
        }

        return new Book();


    }

    @Override
    public Book getBookByName(String bookName) {
        Book bookInStock = bookRepository.findBookByName(bookName);
        return bookInStock;
    }

    @Override
    public Book updateBook(Book book, long id) throws ResourceNotFoundException {
        Book stockBook = bookRepository.getBook(id);

        if(stockBook == null){
            throw new ResourceNotFoundException("Resource is not found");
        }

        Double price = book.getPrice() != null ? book.getPrice() : stockBook.getPrice();
        Long stock = book.getBookCount() != null ? book.getBookCount() : stockBook.getBookCount();

        bookRepository.update(stockBook.getId(), stock, price);

        book.setId(id);
        book.setPrice(price);
        book.setBookCount(stock);
        book.setName(stockBook.getName());
        book.setAuthor(stockBook.getAuthor());
        return book;
    }
}
