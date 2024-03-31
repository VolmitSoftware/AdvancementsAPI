package eu.endercentral.crazy_advancements.nms.api;

import java.util.Objects;

public abstract class Wrapper {
    public Object instance;
    public Wrapper(Object instance) {
        this.instance = instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) instance;
    }

    public void set(Object instance) {
        this.instance = instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wrapper wrapper = (Wrapper) o;
        return Objects.equals(instance, wrapper.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instance);
    }
}
