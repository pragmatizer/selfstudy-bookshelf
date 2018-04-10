package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.sk.cnaps.domain.model.AbstractEntity;
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
public class Author extends AbstractEntity implements AggregateRoot {
    @Column(nullable=false)
    private String name;
    
    private String email;
    
    private String homepage;
    
    public Author(String name) {
    	this.name = name;
    }
    
    public Author(String name, String email, String homepage) {
    	this.name = name;
    	this.email = email;
    	this.homepage = homepage;
    }
}
