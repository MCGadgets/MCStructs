package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtRegistry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringNbt implements INbtTag {

    private String value;

    public StringNbt() {
        this("");
    }

    public StringNbt(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    private String escapeString(String s) {
        StringBuilder builder = new StringBuilder(" ");
        char surround = 0;

        for (char c : s.toCharArray()) {
            if (c == '\\') {
                builder.append('\\');
            } else if (c == '"' || c == '\'') {
                if (surround == 0) surround = c == '"' ? '\'' : '"';
                if (surround == c) builder.append('\\');
            }
            builder.append(c);
        }

        if (surround == 0) surround = '"';
        builder.setCharAt(0, surround);
        builder.append(surround);
        return builder.toString();
    }

    @Override
    public int getId() {
        return NbtRegistry.STRING_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(288);
        this.value = in.readUTF();
        readTracker.read(16 * this.value.length());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    @Override
    public INbtTag copy() {
        return new StringNbt(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringNbt stringNbt = (StringNbt) o;
        return Objects.equals(value, stringNbt.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.escapeString(this.value);
    }

}
