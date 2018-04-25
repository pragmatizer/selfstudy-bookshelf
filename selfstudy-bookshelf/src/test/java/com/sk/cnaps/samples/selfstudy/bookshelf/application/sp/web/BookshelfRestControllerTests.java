package com.sk.cnaps.samples.selfstudy.bookshelf.application.sp.web;

import static com.google.common.collect.Lists.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookshelfRestControllerTests {
	//
	Logger logger = LoggerFactory.getLogger(getClass());

	// Console Color
	private static final String RESET		= "\u001B[0m";	// Text Reset
	private static final String RED			= "\u001B[31m";	// Red
	private static final String BLUE		= "\u001B[34m";	// Blue
	private static final String PURPLE		= "\u001B[35m";	// Purple

	@Rule
	public TestName testName = new TestName();

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

	private Long testAuthorId = 0L;
	private Long testBookshelfId = 0L;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		authorRepository.save(new Author("박지원"));
		authorRepository.save(new Author("프란시스 베이컨"));
		authorRepository.save(new Author("찰스 다윈"));
		authorRepository.save(new Author("정성권", "klimtver@gmail.com", ""));

        logger.info(">>>>> authorRepository " + authorRepository.findAll());

		bookRepository.save(new Book("연암산문선", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("신기관", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("토마스 쿤", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("마이크로서비스 아키텍처 구축", "대용량 시스템의 효율적인 분산 설계 기법", VersionType.TRANSLATION));

		ArrayList<Author> authors = newArrayList(authorRepository.findAll());
		ArrayList<Book> books = newArrayList(bookRepository.findAll());
		
		books.get(0).getAuthorsAggregate().add(authors.get(0).getId());
		books.get(1).getAuthorsAggregate().add(authors.get(1).getId());
		books.get(2).getAuthorsAggregate().add(authors.get(2).getId());
		books.get(3).getAuthorsAggregate().add(authors.get(3).getId());

		testAuthorId = authors.get(0).getId();

		bookRepository.save(books);
		
		Bookshelf bookshelf = new Bookshelf("My bookshelf");
		bookshelf.getBooksAggregate().add(books.get(0).getId());
		bookshelf.getBooksAggregate().add(books.get(1).getId());
		bookshelf.getBooksAggregate().add(books.get(3).getId());

		bookshelfRepository.save(bookshelf);

        ArrayList<Bookshelf> bookshelves = newArrayList(bookshelfRepository.findAll());
        testBookshelfId = bookshelves.get(bookshelves.size() - 1).getId();

        System.out.println("**************** bookshelves " + bookshelves.toString());
        System.out.println("**************** testBookshelfId " + testBookshelfId);
        System.out.println("**************** bookshelves.size() " + bookshelves.size());
        for (int i = 0; i < bookshelves.size(); i++) {
            System.out.println(" ^ "+ bookshelves.get(i));
        }
	}
	
	@Test
	public void testFindBookWithAuthorsById() throws Exception {
		//
		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 starting ----------" + RESET);

		Long authorId = testAuthorId.longValue();

		Book book = service.findBookWithAuthorsById(authorId);

        logger.info(">>>>> book " + book.toString());
		
		mockMvc.perform(get("/v1/bookshelf-service/books:withAuthors/" + authorId))
		       .andExpect(status().isOk())
		       .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		       .andExpect(content().string(equalTo(JsonUtil.toJsonStr(book))));

		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 end ----------" + RESET);
	}

	@Test
	public void testFindBookshelfWithBooksById() throws Exception {
		//
		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 starting ----------" + RESET);

		Long bookshelfId = testBookshelfId.longValue();

		Bookshelf bookshelf = service.findBookshelfWithBooksById(bookshelfId);

        logger.info(">>>>> bookshelf " + bookshelf.toString());

		mockMvc.perform(get("/v1/bookshelf-service/bookshelves:withBooks/" + bookshelfId))
	           .andExpect(status().isNotFound());

		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 end ----------" + RESET);
	}

	@After
	public void tearDown() throws Exception {
		//
	}

}
