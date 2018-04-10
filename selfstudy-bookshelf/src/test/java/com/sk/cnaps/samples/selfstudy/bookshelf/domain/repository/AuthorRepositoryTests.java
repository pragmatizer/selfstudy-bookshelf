package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository.AuthorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryTests {
	@Autowired
	private AuthorRepository repository;
	
	@Test
	public void test() {
		
		repository.save(new Author("박지원"));
		repository.save(new Author("프란시스 베이컨"));
		repository.save(new Author("찰스 다윈"));
		repository.save(new Author("정성권", "klimtver@gmail.com", ""));
		
		
		Iterable<Author> authors = repository.findAll();
		
		assertThat(authors).isNotEmpty().hasSize(4);
	}
}
