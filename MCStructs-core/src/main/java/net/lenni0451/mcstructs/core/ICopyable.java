package net.lenni0451.mcstructs.core;

public interface ICopyable<T> {

    /**
     * Create a copy of this object.<br>
     * All children are copied too.
     *
     * @return The copy
     */
    T copy();

}
