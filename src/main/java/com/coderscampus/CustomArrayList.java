package com.coderscampus;

import java.util.Arrays;

public class CustomArrayList<T> implements CustomList<T> {
    private Object[] items;
    private int size;

    public CustomArrayList() {
        items = new Object[10];
        size = 0;
    }

    @Override
    public boolean add(T item) {
        if (size == items.length) {
            items = doubleSizeOfBackingArray();
        }
        items[size] = item;
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T item) throws IndexOutOfBoundsException {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == items.length) {
            items = doubleSizeOfBackingArray();
        }
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;
        size++;
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) items[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedItem = (T) items[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(items, index + 1, items, index, numMoved);
        }
        items[--size] = null; // Clear to let GC do its work
        return removedItem;
    }

    private Object[] doubleSizeOfBackingArray() {
        return Arrays.copyOf(items, items.length * 2);
    }

}
