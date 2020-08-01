package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserMeditationDao extends com.reflect.generated.tables.daos.UserMeditationDao {
    private final DSLContext dsl;

    public UserMeditationDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }
}
