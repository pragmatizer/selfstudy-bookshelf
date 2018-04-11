package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import com.sk.cnaps.domain.model.AbstractEntity;
import com.sk.cnaps.domain.model.AggregateProxy;
import com.sk.cnaps.domain.model.AggregateRelationType;
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
	@Column(nullable=false)
	private String name;
	
	@Convert(converter=AggregateProxy.class)
	@Column(columnDefinition="TEXT")
	private AggregateProxy<Book> booksAggregate = new AggregateProxy<>(AggregateRelationType.ONE_TO_MANY);
	
	public Bookshelf(String name) {
		super();
		this.name = name;
	}
}
