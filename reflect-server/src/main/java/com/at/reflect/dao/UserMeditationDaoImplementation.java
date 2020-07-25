package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.tables.daos.UserMeditationDao;

@Repository
public class UserMeditationDaoImplementation extends UserMeditationDao {
	private final DSLContext dsl;

	public UserMeditationDaoImplementation(final DSLContext dsl) {
		super(dsl.configuration());
		this.dsl = dsl;
	}
}
