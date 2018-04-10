package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AuthorRepositoryTests {
	@Autowired
	private AuthorRepository repository;
	
	//@Test
	public void test() {
		
		repository.save(new Author("박지원"));
		repository.save(new Author("프란시스 베이컨"));
		repository.save(new Author("찰스 다윈"));
		repository.save(new Author("정성권", "klimtver@gmail.com", ""));
		
		
		Iterable<Author> authors = repository.findAll();
		
		assertThat(authors).isNotEmpty().hasSize(4);
	}
}
