package com.retail.readingisgood.controller;

import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Book;
import com.retail.readingisgood.services.BookService;
import com.retail.readingisgood.services.impl.BookServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value="Book Service")
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/list")
    private ResponseEntity<List<Book>> getAllBooks(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Book> response = bookService.getAllBooks(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<Book>>(response, HttpStatus.OK);
    }

    @GetMapping("/retrieve/{id}")
    private ResponseEntity<Book> retriveBookById(@PathVariable("id") long id) throws ResourceNotFoundException {

        return new ResponseEntity<Book>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/addNewBook")
    private ResponseEntity<Book> addBookToStock(@RequestBody Book book) throws ResourceExistsException {
        return new ResponseEntity<Book>(bookService.saveBookToStock(book), HttpStatus.OK);
    }

    @PostMapping("/update/{id}/count/{count}")
    private ResponseEntity<Book> updateStockByID(@PathVariable("id") long id, @PathVariable("count") long count)
    {
        return new ResponseEntity<Book>(bookService.updateBookStock(id, count), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    private ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) throws ResourceNotFoundException {
        Book updatedBook = bookService.updateBook(book, id);
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
    }

}

