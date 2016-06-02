package org.ababup1192;

import org.mongodb.morphia.annotations.Embedded;

import java.util.List;
import java.util.Objects;

class ListEntity {
    private Integer id;
    @Embedded
    public List<EmbeddedEntity> embeddedEntities;

    public ListEntity() {
    }

    public ListEntity(Integer id, List<EmbeddedEntity> embeddedEntities) {
        this.id = id;
        this.embeddedEntities = embeddedEntities;
    }

    @Override
    public String toString() {
        return id + ", " + embeddedEntities;
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) return true;
        else if (!(target instanceof ListEntity)) {
            return false;
        } else {
            ListEntity castedTarget = (ListEntity) target;
            return Objects.equals(this.embeddedEntities, castedTarget.embeddedEntities);
        }
    }
}
