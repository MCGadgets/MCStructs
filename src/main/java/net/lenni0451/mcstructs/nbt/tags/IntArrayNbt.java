package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtRegistry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntArrayNbt implements INbtTag {

    private int[] value;

    public IntArrayNbt(final int[] value) {
        this.value = value;
    }

    public int[] getValue() {
        return this.value;
    }

    public void setValue(final int[] value) {
        this.value = value;
    }

    public int get(final int index) {
        return this.value[index];
    }

    public void set(final int index, final int i) {
        this.value[index] = i;
    }

    public void add(final int i) {
        int[] newValue = new int[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = i;
        this.value = newValue;
    }

    public int getLength() {
        return this.value.length;
    }

    public boolean isEmpty() {
        return this.value.length == 0;
    }

    @Override
    public int getId() {
        return NbtRegistry.INT_ARRAY_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(192);
        int length = in.readInt();
        readTracker.read(32 * length);
        this.value = new int[length];
        for (int i = 0; i < this.value.length; i++) this.value[i] = in.readInt();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        for (int i : this.value) out.writeInt(i);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[I;");
        for (int i = 0; i < this.value.length; i++) out.append(i).append(",");
        if (this.value.length > 0) out.deleteCharAt(out.length() - 1);
        return out.append("]").toString();
    }

}
