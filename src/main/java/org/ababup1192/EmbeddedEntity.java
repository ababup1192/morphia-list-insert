package org.ababup1192;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Objects;

@Embedded
public class EmbeddedEntity implements Cloneable {
    public Integer id;
    public String text;
    public Boolean disable;

    public EmbeddedEntity() {
    }

    public EmbeddedEntity(Integer id, String text, Boolean disable) {
        this.id = id;
        this.text = text;
        this.disable = disable;
    }

    @Override
    public String toString() {
        return id + ", " + text + ", " + disable;
    }

    @Override
    public boolean equals(Object target) {
        if (this == target) return true;
        else if (!(target instanceof EmbeddedEntity)) {
            return false;
        } else {
            EmbeddedEntity castedTarget = (EmbeddedEntity) target;
            return Objects.equals(this.id, castedTarget.id) &&
                    Objects.equals(this.text, castedTarget.text) &&
                    Objects.equals(this.disable, castedTarget.disable);
        }
    }

    @Override
    public EmbeddedEntity clone() {
        EmbeddedEntity embeddedEntity = null;

        try {
            embeddedEntity = (EmbeddedEntity) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return embeddedEntity;
    }
}
