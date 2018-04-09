package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;

@RepositoryRestResource
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
