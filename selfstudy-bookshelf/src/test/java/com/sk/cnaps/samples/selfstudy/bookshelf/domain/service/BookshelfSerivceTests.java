package com.sk.cnaps.samples.selfstudy.bookshelf.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	//
	Logger logger = LoggerFactory.getLogger(getClass());

	// Console Color
	private static final String RESET		= "\u001B[0m";	// Text Reset
	private static final String RED			= "\u001B[31m";	// Red
	private static final String BLUE		= "\u001B[34m";	// Blue
	private static final String PURPLE		= "\u001B[35m";	// Purple

	@Rule
	public TestName testName = new TestName();

	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		public void starting(Description desc) {
			logger.info(BLUE + "========== " + desc.getClassName() + " starting ==========" + RESET);
		}

		@Override
		public void succeeded(Description desc) {
			logger.info(BLUE + "========== " + desc.getClassName() + " succeeded ==========" + RESET);
		}

		@Override
		public void failed(Throwable e, Description desc) {
			logger.info(RED + "========== " + desc.getClassName() + " failed ==========" + RESET);
			logger.error("", e);
		}
	};

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookshelfRepository bookshelfRepository;

	@Autowired
	private BookshelfService bookshelfService;

	/**
	 * 단위 테스트 안의 모든 메소드 실행 전에 한번만 수행
	 *
	 * JUnit5의 경우 @BeforeAll
	 * JUnit4의 경우 @BeforeClass
	 */
	@BeforeClass
	public static void initAll() {
		//
	}

	/**
	 * 단위 테스트 안의 각 메소드가 실행될 때마다 실행 전에 수행
	 *
	 * JUnit5의 경우 @BeforeEach
	 * Junit4의 경우 @Before
	 *
	 * @throws Exception 에러처리
	 */
	@Before
	public void init() throws Exception {
		//
		authorRepository.deleteAll();
		bookRepository.deleteAll();
		bookshelfRepository.deleteAll();

		authorRepository.save(new Author("박지원"));
		authorRepository.save(new Author("프란시스 베이컨"));
		authorRepository.save(new Author("찰스 다윈"));
		authorRepository.save(new Author("정성권", "klimtver@gmail.com", ""));

		bookRepository.save(new Book("연암산문선", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("신기관", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("토마스 쿤", "", VersionType.ORIGINAL));
		bookRepository.save(new Book("마이크로서비스 아키텍처 구축", "대용량 시스템의 효율적인 분산 설계 기법", VersionType.TRANSLATION));

		logger.info(PURPLE + "사전 설정된 bookRepository : " + bookRepository.findAll().toString() + RESET);
	}

	/**
	 * testFindBookshelfWithBooksById
	 */
	@Test
	public void testFindBookshelfWithBooksById() {
		//
		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 starting ----------" + RESET);

		// given
		ArrayList<Author> authors = Lists.newArrayList(authorRepository.findAll());
		ArrayList<Book> books = Lists.newArrayList(bookRepository.findAll());

		books.get(0).getAuthorsAggregate().add(authors.get(0).getId());
		books.get(1).getAuthorsAggregate().add(authors.get(1).getId());
		books.get(2).getAuthorsAggregate().add(authors.get(2).getId());
		books.get(3).getAuthorsAggregate().add(authors.get(3).getId());

		Bookshelf bookshelf = new Bookshelf("My bookshelf");
		bookshelf.getBooksAggregate().add(books.get(0).getId());
		bookshelf.getBooksAggregate().add(books.get(1).getId());
		bookshelf.getBooksAggregate().add(books.get(2).getId());
		bookshelf.getBooksAggregate().add(books.get(3).getId());

		bookshelfRepository.save(bookshelf);

		// when
		Bookshelf bookshelfWithBooks = bookshelfService.findBookshelfWithBooksById(bookshelf.getId());

		// then
		Map<Long, Book> booksFromSvc = bookshelfWithBooks.getBooksAggregate().getIdValues();
		logger.info(PURPLE + "Service를 통해 조회한 데이터 Size : " + booksFromSvc.size() + RESET);

		Iterator<Book> booksFromDB = ((Iterable<Book>) bookRepository.findAll()).iterator();
		logger.info(PURPLE + "Repository에 저장되어 있는 데이터 Count : " + bookRepository.count() + RESET);

		Map<Long, Book> bookMapFromDB = new HashMap<Long, Book>();
		while (booksFromDB.hasNext()) {
			Book bookFromDB = booksFromDB.next();
			bookMapFromDB.put(bookFromDB.getId(), bookFromDB);
		}

		logger.info(PURPLE + "Service를 통해 조회한 데이터는 비어 있지 않고 사이즈는 4이다" + RESET);
		assertThat(bookshelfWithBooks.getBooksAggregate().getValues()).isNotEmpty().hasSize(4);

		logger.info(PURPLE + "Service 조회 데이터와 Repository 저장 데이터는 같다" + RESET);
		assertThat(booksFromSvc, equalTo(bookMapFromDB));

		authorRepository.deleteAll();
		bookRepository.deleteAll();
		bookshelfRepository.deleteAll();

		logger.info(BLUE + "---------- " + testName.getMethodName() + "() 메소드 end ----------" + RESET);
	}

	/**
	 * 단위 테스트 안의 각 메소드가 실행될 때마다 실행 후에 수행
	 *
	 * JUnit5의 경우 @AfterEach
	 * Junit4의 경우 @After
	 *
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		//
	}

	/**
	 * 단위 테스트 안의 모든 메소드 실행이 된 후 마지막에 한번만 수행
	 *
	 * JUnit5의 경우 @AfterAll
	 * Junit4의 경우 @AfterClass
	 */
	@AfterClass
	public static void tearDownAll() {
		//
	}
}