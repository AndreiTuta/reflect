package com.at.reflect.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.Tables;
import com.reflect.generated.tables.pojos.Meditation;

@Repository
public class MeditationDao extends com.reflect.generated.tables.daos.MeditationDao {

    private final DSLContext dsl;

    public MeditationDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

//    public int insertReturnId(final Meditation meditation) {
//        return dsl.insertInto(Tables.MEDITATION)
//                  .set(dsl.newRecord(Tables.MEDITATION, meditation))
//                  .returningResult(Tables.MEDITATION.ID)
//                  .fetchOne()
//                  .into(int.class);
//    }
}
