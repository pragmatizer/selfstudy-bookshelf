package com.sk.cnaps.samples.selfstudy.bookshelf.application.sp.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.service.logic.BookshelfLogic;

@RestController
@RequestMapping
@Transactional
public class BookshelfRestController {
	@Autowired
	private BookshelfLogic logic;

	@GetMapping("v1/bookshelf-service/books:withAuthors/{id}")
	public ResponseEntity<Book> findBookWithAuthorsById(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(logic.findBookWithAuthorsById(id), HttpStatus.OK);
		} catch(NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
	}

	@GetMapping("v1/bookshelf-serivce/bookshelves:withBooks/{id}")
	public ResponseEntity<Bookshelf> findBookshelfWithBooksById(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(logic.findBookshelfWithBooksById(id), HttpStatus.OK);
		} catch(NullPointerException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
