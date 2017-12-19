package io.sunshower.persistence.core;

import javax.persistence.MappedSuperclass;
import java.util.Set;

/**
 * Created by haswell on 3/27/17.
 */
@MappedSuperclass
public interface Taggable {

    Set<Tags> getTags();

    default void addTag(Tag tag) {
        final Tags t = new Tags();
        t.setType(getType());
        t.setTag(tag);
        getTags().add(t);
    }

    default Class<?> getType() {
        return getClass();
    }

    boolean hasTag(Tag tag);
}
