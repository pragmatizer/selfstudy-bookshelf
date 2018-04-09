package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import java.util.Set;

import com.sk.cnaps.domain.model.ValueObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Contents implements ValueObject {
	private Set<String> chapters;
}
