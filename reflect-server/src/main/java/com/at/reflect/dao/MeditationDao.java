package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class MeditationDao extends com.reflect.generated.tables.daos.MeditationDao {

    private final DSLContext dsl;

    public MeditationDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

}
