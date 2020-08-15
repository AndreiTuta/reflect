package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDao extends com.reflect.generated.tables.daos.EmailDao {

    private final DSLContext dsl;

    public EmailDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

}
