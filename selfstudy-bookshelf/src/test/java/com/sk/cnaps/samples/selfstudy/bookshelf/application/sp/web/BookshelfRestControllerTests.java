package com.sk.cnaps.samples.selfstudy.bookshelf.application.sp.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.collect.Lists;
import com.sk.cnaps.domain.util.JsonUtil;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.VersionType;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.AuthorRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.BookshelfRepository;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.service.BookshelfService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookshelfRestControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookshelfRestController controller;
	
	@Autowired
	private BookshelfService service;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookshelfRepository bookshelfRepository;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
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
		
		books.get(0).addAuthorId(authors.get(0).getId());
		books.get(1).addAuthorId(authors.get(1).getId());
		books.get(2).addAuthorId(authors.get(2).getId());
		books.get(3).addAuthorId(authors.get(3).getId());
				
		bookRepository.save(books);
		
		Bookshelf bookshelf = new Bookshelf("My bookshelf");
		bookshelf.addBookId(books.get(0).getId());
		bookshelf.addBookId(books.get(1).getId());
		bookshelf.addBookId(books.get(3).getId());
		
		bookshelfRepository.save(bookshelf);

	}
	
	@Test
	public void testFindBookWithAuthorsById() throws Exception {
		Long authorId = 1L;
			
		Book book = service.findBookWithAuthorsById(authorId);
		
		mockMvc.perform(get("/v1/bookshelf-service/books:withAuthors/" + authorId))
		       .andExpect(status().isOk())
		       .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		       .andExpect(content().string(equalTo(JsonUtil.toJsonStr(book))));
	}

	@Test
	public void testFindBookshelfWithBooksById() throws Exception {
		Long bookshelfId = 2L;
		
		Bookshelf bookshelf = service.findBookshelfWithBooksById(bookshelfId);
		
		System.out.println("*********************************************************************");
		System.out.println(bookshelf.toString());
	
		mockMvc.perform(get("/v1/bookshelf-service/bookshelves:withBooks/" + bookshelfId))
	           .andExpect(status().isNotFound());
	}
	
}
