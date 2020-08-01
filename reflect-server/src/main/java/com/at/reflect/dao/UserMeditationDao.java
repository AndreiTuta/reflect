package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.Tables;
import com.reflect.generated.tables.pojos.UserMeditation;

@Repository
public class UserMeditationDao extends com.reflect.generated.tables.daos.UserMeditationDao {
    private final DSLContext dsl;

    public UserMeditationDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

    public void insertUserMeditation(UserMeditation userMeditation) {
        dsl.insertInto(Tables.USER_MEDITATION)
           .values(userMeditation)
           .execute();
    }
}
