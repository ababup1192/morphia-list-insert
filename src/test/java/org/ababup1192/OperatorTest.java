package org.ababup1192;

import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class OperatorTest {
    private static final String DATA_STORE_NAME = "list";

    @Before
    public void preProcess() throws Exception {
        final Morphia morphia = new Morphia();

        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase(DATA_STORE_NAME);

        final Datastore datastore = morphia.createDatastore(new MongoClient(), DATA_STORE_NAME);

        datastore.save(new ListEntity(1, Arrays.asList(
                new EmbeddedEntity(1001, "text", false),
                new EmbeddedEntity(1002, "text2", false),
                new EmbeddedEntity(1003, "text3", false)
        )));
        datastore.save(new ListEntity(2, Arrays.asList(
                new EmbeddedEntity(2001, "text", false),
                new EmbeddedEntity(2002, "text2", false),
                new EmbeddedEntity(2003, "text3", false)
        )));
        datastore.save(new ListEntity(3, Arrays.asList(
                new EmbeddedEntity(3001, "text", false),
                new EmbeddedEntity(3002, "text2", false),
                new EmbeddedEntity(3003, "text3", false)
        )));
    }

    @Test
    public void testSelectIntegerEntity() {
        final Morphia morphia = new Morphia();
        final Datastore datastore = morphia.createDatastore(new MongoClient(), DATA_STORE_NAME);
        // 変更の対象となるのは、idが1であるListEntity
        final Query<ListEntity> selectQuery = datastore.createQuery(ListEntity.class).filter("id", 1);
        final Query<ListEntity> updatedQuery = datastore.createQuery(ListEntity.class).filter("id", 2);

        ListEntity updateEntity = updatedQuery.asList().get(0);

        UpdateOperations<ListEntity> removeOperation = datastore.
                createUpdateOperations(ListEntity.class).set("embeddedEntities", new ArrayList<>());

        // 要素追加のオペレーションを用意
        UpdateOperations<ListEntity> insertOperation = datastore.
                createUpdateOperations(ListEntity.class).addAll("embeddedEntities", updateEntity.embeddedEntities, true);

        // 削除フラグが立っているEmbeddedEntityをinsert
        datastore.update(selectQuery, removeOperation);
        datastore.update(selectQuery, insertOperation);

        // 比較テスト
        final List<ListEntity> listEntities = selectQuery.asList();

        assertThat(listEntities.get(0), is(new ListEntity(1, Arrays.asList(
                new EmbeddedEntity(2001, "text", false),
                new EmbeddedEntity(2002, "text2", false),
                new EmbeddedEntity(2003, "text3", false)
        ))));
    }
}
