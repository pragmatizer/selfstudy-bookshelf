package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="books", path="books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}
