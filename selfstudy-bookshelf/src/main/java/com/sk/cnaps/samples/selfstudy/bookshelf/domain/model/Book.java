package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sk.cnaps.domain.model.AbstractEntity;
import com.sk.cnaps.domain.model.AggregateProxy;
import com.sk.cnaps.domain.model.AggregateRelationType;
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
public class Book extends AbstractEntity implements AggregateRoot {
    @Column(nullable=false)
    private String title;

    private String subtitle;
    
    @Convert(converter=Contents.class)
    @Column(columnDefinition="TEXT")
    private Contents contents;
    
    @Enumerated(value=EnumType.STRING)
    @Column(nullable=false)
    private VersionType versionType;
    
    @Convert(converter=AggregateProxy.class)
    @Column(columnDefinition="TEXT")
    private AggregateProxy<Author> authorsAggregate = new AggregateProxy<>(AggregateRelationType.ONE_TO_MANY);
    
    public Book(String title, String subtitle, VersionType versionType) {
    	this.title = title;
    	this.subtitle = subtitle;
    	this.versionType = versionType;
    }
}




