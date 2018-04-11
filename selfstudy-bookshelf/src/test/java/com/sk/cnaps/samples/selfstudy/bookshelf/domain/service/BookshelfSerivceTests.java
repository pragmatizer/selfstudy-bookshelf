package com.sk.cnaps.samples.selfstudy.bookshelf.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.VersionType;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.AuthorRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookshelfRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookshelfSerivceTests {
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookshelfRepository bookshelfRepository;
	
	@Autowired
	private BookshelfService bookshelfService;
	
	@Test
	public void test() {	
		authorRepository.save(new Author("박지원"));
		authorRepository.save(new Author("프란시스 베이컨"));
		authorRepository.save(new Author("찰스 다윈"));
		authorRepository.save(new Author("정성권", "klimtver@gmail.com", ""));
		
		bookRepository.save(new Book("연암산문선", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("신기관", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("토마스 쿤", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("마이크로서비스 아키텍처 구축", "대용량 시스템의 효율적인 분산 설계 기법", VersionType.TRANSLATION));
		
		ArrayList<Author> authors = Lists.newArrayList(authorRepository.findAll());
		ArrayList<Book> books = Lists.newArrayList(bookRepository.findAll());
		
		books.get(0).getAuthorsAggregate().add(authors.get(0).getId());
		books.get(1).getAuthorsAggregate().add(authors.get(1).getId());
		books.get(2).getAuthorsAggregate().add(authors.get(2).getId());
		books.get(3).getAuthorsAggregate().add(authors.get(3).getId());
				
		bookRepository.save(books);
		
		Bookshelf bookshelf = new Bookshelf("My bookshelf");
		bookshelf.getBooksAggregate().add(books.get(0).getId());
		bookshelf.getBooksAggregate().add(books.get(1).getId());
		bookshelf.getBooksAggregate().add(books.get(3).getId());
		
		bookshelfRepository.save(bookshelf);
		
		Bookshelf bookshelfWithBooks = bookshelfService.findBookshelfWithBooksById(bookshelf.getId());
				
		assertThat(bookshelfWithBooks.getBooksAggregate().getValues()).isNotEmpty().hasSize(3);
		
	}
}
