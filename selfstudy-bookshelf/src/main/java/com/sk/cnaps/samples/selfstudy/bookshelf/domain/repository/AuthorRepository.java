package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Author;

@RepositoryRestResource(collectionResourceRel="authors", path="authors")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
	public Set<Author> findByIdIn(@Param("ids") Set<Long> ids);
}
