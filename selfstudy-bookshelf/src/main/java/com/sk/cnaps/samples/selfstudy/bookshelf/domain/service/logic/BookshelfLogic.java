package com.sk.cnaps.samples.selfstudy.bookshelf.domain.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Contents;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.AuthorRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookshelfRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.service.BookshelfService;

@Service
public class BookshelfLogic implements BookshelfService {
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookshelfRepository bookshelfRepository;

	@Override
	public Book findBookWithAuthorsById(Long id) {
		Book book = bookRepository.findOne(id);
		if(book == null) {
			throw new NullPointerException();
		}
		book.setAuthors(authorRepository.findByIdIn(book.getAuthorIds()));
		return book;
	}

	@Override
	public Bookshelf findBookshelfWithBooksById(Long id) {
		Bookshelf bookshelf = bookshelfRepository.findOne(id);
		if(bookshelf == null) {
			throw new NullPointerException();
		}
		bookshelf.setBooks(bookRepository.findByIdIn(bookshelf.getBookIds()));
		return bookshelf;
	}	
	
}
