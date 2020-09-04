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

    public boolean updateUserMeditation(UserMeditation userMeditation) {
        return dsl.update(Tables.USER_MEDITATION)
                  .set(Tables.USER_MEDITATION.USER_ID, userMeditation.getUserId())
                  .set(Tables.USER_MEDITATION.USER_MEDITATION_TEXT, userMeditation.getUserMeditationText())
                  .set(Tables.USER_MEDITATION.MEDITATION_ID, userMeditation.getMeditationId())
                  .execute() > 0;
    }

    public boolean insertUserMeditation(UserMeditation userMeditation) {
        return dsl.insertInto(Tables.USER_MEDITATION)
                  .set(Tables.USER_MEDITATION.USER_ID, userMeditation.getUserId())
                  .set(Tables.USER_MEDITATION.USER_MEDITATION_TEXT, userMeditation.getUserMeditationText())
                  .set(Tables.USER_MEDITATION.MEDITATION_ID, userMeditation.getMeditationId())
                  .execute() > 0;
    }

    public boolean deleteUserMeditation(UserMeditation userMeditation) {
        return dsl.deleteFrom(Tables.USER_MEDITATION)
                  .where(Tables.USER_MEDITATION.USER_ID.eq(userMeditation.getUserId())
                                                       .and(Tables.USER_MEDITATION.MEDITATION_ID.eq(userMeditation.getMeditationId())))
                  .execute() > 0;
    }

    public UserMeditation fetchUserMeditationByIds(int medId, int userIntId) {
        return dsl.selectFrom(Tables.USER_MEDITATION)
                  .where(Tables.USER_MEDITATION.USER_ID.eq(userIntId)
                                                       .and(Tables.USER_MEDITATION.MEDITATION_ID.eq(medId)))
                  .fetchOneInto(UserMeditation.class);
    }
}
