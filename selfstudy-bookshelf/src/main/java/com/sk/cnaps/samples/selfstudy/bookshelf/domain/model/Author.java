package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

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
public class Author extends AbstractPersistable<Long> implements AggregateRoot {
    @Column(nullable=false)
    private String name;
}
