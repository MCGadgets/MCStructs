package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListTag<T extends INbtTag> implements INbtTag, Iterable<T> {

    private NbtType type;
    private List<T> value;

    public ListTag() {
        this(null, new ArrayList<>());
    }

    public ListTag(final NbtType type) {
        this(type, new ArrayList<>());
    }

    public ListTag(final List<T> list) {
        if (!list.isEmpty()) {
            this.type = NbtType.byClass(list.get(0).getClass());
            if (list.stream().anyMatch(tag -> !tag.getNbtType().equals(this.type))) throw new IllegalArgumentException("Tried to create list with multiple nbt types");
        }
        this.value = list;
    }

    public ListTag(final NbtType type, final List<T> value) {
        this.type = type;
        this.value = value;
    }

    public NbtType getType() {
        return this.type;
    }

    public void setType(final NbtType type) {
        this.type = type;
    }

    public List<T> getValue() {
        return this.value;
    }

    public void setValue(final List<T> value) {
        this.value = value;
    }

    public T get(final int index) {
        return this.value.get(index);
    }

    public void add(final T tag) {
        this.check(tag);
        this.value.add(tag);
    }

    public void set(final int index, final T tag) {
        this.check(tag);
        this.value.set(index, tag);
    }

    public void remove(final T tag) {
        this.check(tag);
        this.value.remove(tag);
    }

    public boolean canAdd(final INbtTag tag) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(tag.getNbtType());
    }

    public boolean canAdd(final NbtType type) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(type);
    }

    public boolean trim() {
        if (this.value.isEmpty()) return true;
        if (NbtType.COMPOUND.equals(this.type)) this.value.forEach(tag -> ((CompoundTag) tag).trim());
        else if (NbtType.LIST.equals(this.type)) this.value.forEach(tag -> ((ListTag<?>) tag).trim());
        return false;
    }

    public int size() {
        return this.value.size();
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    private void check(final T tag) {
        if (this.type == null || this.value.isEmpty()) {
            this.type = tag.getNbtType();
            this.value.clear();
        } else if (!this.type.equals(tag.getNbtType())) {
            throw new IllegalArgumentException("Can't add " + tag.getClass().getSimpleName() + " to a " + this.type.name() + " list");
        }
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.LIST;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(37);
        int typeId = in.readByte();
        int count = in.readInt();
        if (typeId == NbtType.END.getId() && count > 0) throw new NbtReadException("ListNbt with type END and count > 0");
        readTracker.read(4 * count);
        this.type = NbtType.byId(typeId);
        this.value = new ArrayList<>(Math.min(count, 512));
        for (int i = 0; i < count; i++) {
            T tag = (T) this.type.newInstance();
            readTracker.pushDepth();
            tag.read(in, readTracker);
            readTracker.popDepth();
            this.value.add(tag);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeByte(this.type.getId());
        out.writeInt(this.value.size());
        for (T tag : this.value) tag.write(out);
    }

    @Override
    public INbtTag copy() {
        List<INbtTag> value = new ArrayList<>();
        for (T val : this.value) value.add(val.copy());
        return new ListTag<>(value);
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTag<?> listTag = (ListTag<?>) o;
        return Objects.equals(type, listTag.type) && Objects.equals(value, listTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        String list = this.value.toString();
        list = list.substring(1, list.length() - 1);
        return "List[" + this.value.size() + "](" + list + ")";
    }

}