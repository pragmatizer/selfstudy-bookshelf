package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Author extends AbstractPersistable<Long> {
    @Column(nullable=false)
    private String name;
}
