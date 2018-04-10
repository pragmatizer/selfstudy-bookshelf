package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;

@RepositoryRestResource(collectionResourceRel="books", path="books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
	public Set<Book> findByIdIn(@Param("ids") Set<Long> ids);
}
