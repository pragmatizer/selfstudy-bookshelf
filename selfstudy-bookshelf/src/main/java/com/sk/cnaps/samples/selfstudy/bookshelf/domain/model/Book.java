package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Transient;

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

    @ElementCollection(fetch=FetchType.EAGER)
    private Set<Long> authorIds = new HashSet<Long>();
    private transient Set<Author> authors;
    
    public Book(String title, String subtitle, VersionType versionType) {
    	this.title = title;
    	this.subtitle = subtitle;
    	this.versionType = versionType;
    }
    
    public void addAuthorId(Long id) {
    	authorIds.add(id);
    }
    
    public void removeAuthorId(Long id) {
    	authorIds.remove(id);
    }
}




