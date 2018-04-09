package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.sk.cnaps.domain.model.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Bookshelf extends AbstractPersistable<Long> implements AggregateRoot {
	@OneToMany
	private Set<Book> books;
	
	private String owner;
}
