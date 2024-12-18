package com.coderscampus;

public class CustomArrayList<T> implements CustomList<T> {
    Object[] items = new Object[10];
    int size = 0;

    @Override
    public boolean add(Integer index, T item) {
        if (size == items.length) {
            items = doubleSizeOfBackingArray();
        }
        items[size] = item;
        size++;
        return true;
    }

    @Override
    public boolean add(T item) {
        return false;
    }

    @Override
    public void add(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == items.length) {
            items = doubleSizeOfBackingArray();
        }
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = element;
        size++;
    }

    public Object[] doubleSizeOfBackingArray() {
        Object[] newArray = new Object[size * 2];
        if (size >= 0) System.arraycopy(items, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size)
            throw new IndexOutOfBoundsException("The index, " + index + ", is out of the bounds of the array with size " + size);
        return (T) items[index];
    }

    @Override
    public void remove(Integer index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;
        size--;
    }
}
