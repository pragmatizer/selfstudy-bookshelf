package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import com.sk.cnaps.domain.model.AggregateRoot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Book extends AbstractPersistable<Long> implements AggregateRoot {
    @Column(nullable=false)
    private String title;

    private String subtitle;

    @ElementCollection
    private Set<Long> authorIds;
    private transient Set<Author> authors;
}




