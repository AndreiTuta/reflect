package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao extends com.reflect.generated.tables.daos.PaymentDao {

    @SuppressWarnings("unused")
    private final DSLContext dsl;

    public PaymentDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

}
