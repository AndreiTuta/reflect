package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.tables.daos.UserDao;

@Repository
public class UserDaoImplementation extends UserDao {

	private final DSLContext dsl;

	public UserDaoImplementation(final DSLContext dsl) {
		super(dsl.configuration());
		this.dsl = dsl;
	}

}
