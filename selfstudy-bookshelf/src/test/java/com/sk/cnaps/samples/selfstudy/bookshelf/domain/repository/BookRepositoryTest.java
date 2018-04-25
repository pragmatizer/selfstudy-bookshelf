package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.VersionType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {
	@Autowired
	private BookRepository repository;
	
	@Test
	public void test() {
		repository.save(new Book("연암산문선", "", VersionType.ORIGINAL));
		repository.save(new Book("신기관", "", VersionType.ORIGINAL));
		repository.save(new Book("토마스 쿤", "", VersionType.ORIGINAL));
		repository.save(new Book("마이크로서비스 아키텍처 구축", "대용량 시스템의 효율적인 분산 설계 기법", VersionType.TRANSLATION));
		
		Iterable<Book> books = repository.findAll();
		
		assertThat(books).isNotEmpty().hasSize(4);
	}

}