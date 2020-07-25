package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.tables.daos.MeditationDao;

@Repository
public class MeditationDaoImplementation extends MeditationDao {

	private final DSLContext dsl;

	public MeditationDaoImplementation(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

}
