package com.sk.cnaps.samples.selfstudy.bookshelf.domain.service;

import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Book;
import com.sk.cnaps.samples.selfstudy.bookshelf.domain.model.Bookshelf;

public interface BookshelfService {
	Book findBookWithAuthorsById(Long id);
	Bookshelf findBookshelfWithBooksById(Long id);
}
