package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.sk.cnaps.domain.model.AbstractEntity;
import com.sk.cnaps.domain.model.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class Bookshelf extends AbstractEntity implements AggregateRoot {
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Long> bookIds = new HashSet<Long>();
	private transient Set<Book> books;
	
	@Column(nullable=false)
	private String owner;
	
	public Bookshelf(String owner) {
		super();
		this.owner = owner;
	}
	
	public void addBookId(Long id) {
		bookIds.add(id);
	}
	
	public void removeBookId(Long id) {
		bookIds.remove(id);
	}
}
