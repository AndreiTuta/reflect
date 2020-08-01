package com.at.reflect.dao;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.reflect.generated.Tables;
import com.reflect.generated.tables.pojos.Submeditation;

@Repository
public class SubmeditationDao extends com.reflect.generated.tables.daos.SubmeditationDao {

    private final DSLContext dsl;

    public SubmeditationDao(final DSLContext dsl) {
        super(dsl.configuration());
        this.dsl = dsl;
    }

    public void save(final List<Submeditation> submeditation) {
        dsl.insertInto(Tables.SUBMEDITATION)
           .values(submeditation)
           .onDuplicateKeyUpdate()
           .set(dsl.newRecord(Tables.SUBMEDITATION, submeditation))
           .execute();
    }

    public Map<Integer, List<Submeditation>> fetchByMedId(final List<Integer> meditationId) {
        return dsl.selectFrom(Tables.SUBMEDITATION)
                  .where(Tables.SUBMEDITATION.ID.in(meditationId))
                  .fetch()
                  .intoGroups(Tables.SUBMEDITATION.PARENT_MEDITATION_ID, Submeditation.class);
    }

}
