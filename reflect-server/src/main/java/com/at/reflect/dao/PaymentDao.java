package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.Tables;
import com.reflect.generated.tables.pojos.Payment;

@Repository
public class PaymentDao extends com.reflect.generated.tables.daos.PaymentDao {

    private final DSLContext dsl;

    public PaymentDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

    public void delete(Payment formerPayment) {
        dsl.delete(Tables.PAYMENT)
           .where(Tables.PAYMENT.ID.eq(formerPayment.getId()))
           .execute();
    }
}
