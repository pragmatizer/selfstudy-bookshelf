package com.sk.cnaps.samples.selfstudy.bookshelf.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.sk.cnaps.domain.model.ValueObject;
import com.sk.cnaps.domain.util.JsonUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Contents implements ValueObject<Contents> {
	private Set<String> chapters = new HashSet<>();

	@Override
	public Contents convertToEntityAttribute(String dbData) {
		return JsonUtil.fromJsonStr(dbData, Contents.class);
	}
}

