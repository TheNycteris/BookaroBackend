package com.bookaro.api.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookaro.api.models.Book;
import com.bookaro.api.services.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/name/{name}")
	public Book findByName(@PathVariable("name") String name) {
		return bookService.findByName(name);
	}

	@GetMapping("/all")
	public ArrayList<Book> getAllBooks(){
		return bookService.findAll();
	}
	
	@GetMapping (value = "{id}")
	public Optional<Book> getBookId (@PathVariable ("id")long id) {
		return bookService.findById(id);
	}
	
	@PostMapping("")
	public String addBook (@RequestBody Book book) {
		if (book != null) {
			bookService.add(book);
			return "Added a book";
		} else {
			return "Request does not contain a body";
		}
	}
	
	@PutMapping("")
	public String updateBook(@RequestBody Book book) {
	    if(book != null) {
	    	bookService.update(book);
	        return "Updated book.";
	    } else {
	        return "Request does not contain a body";
	    }
	}
	
	@DeleteMapping("{id}")
	public String deleteBook (@PathVariable("id") long id) {
		if(id > 0) {
			if(bookService.delete(id)) {
				return "Deleted the book.";
			} else {
				return "Cannot delete the book.";
			}
		}
		return "The id is invalid for the book.";
	}

}
