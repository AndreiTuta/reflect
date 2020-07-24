package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends com.reflect.generated.tables.daos.UserDao {

	private final DSLContext dsl;

	public UserDao(final DSLContext dsl) {
		super(dsl.configuration());
		this.dsl = dsl;
	}

}
