package com.sk.cnaps.samples.selfstudy.bookshelf.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;

@RepositoryRestResource(collectionResourceRel="bookshelves", path="bookshelves")
public interface BookshelfRepository extends PagingAndSortingRepository<Bookshelf, Long> {
}
