package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Objects;

public class IntTag implements INbtNumber {

    private int value;

    /**
     * Create a int tag with the value 0.
     */
    public IntTag() {
        this(0);
    }

    /**
     * Create a int tag with the given value.
     *
     * @param value The value
     */
    public IntTag(final int value) {
        this.value = value;
    }

    /**
     * @return The int value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Set the int value.
     *
     * @param value The new value
     * @return This tag
     */
    public IntTag setValue(final int value) {
        this.value = value;
        return this;
    }

    @Override
    public byte byteValue() {
        return (byte) (this.value & 0xFF);
    }

    @Override
    public short shortValue() {
        return (byte) (this.value & 0xFFFF);
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public Number numberValue() {
        return this.value;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.INT;
    }

    @Override
    public INbtTag copy() {
        return new IntTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntTag intTag = (IntTag) o;
        return value == intTag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "int(" + this.value + ")";
    }

}
