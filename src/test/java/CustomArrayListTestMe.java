package com.coderscampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CustomArrayListTestMe<T> {

    private CustomList<T> sut;

    @BeforeEach
    void setUp() {
        sut = new CustomArrayList<>();
    }

    void populate(Integer amount) {
        for (Integer i = 0; i < amount; i++) {
            sut.add((T) i);
        }
        assertEquals(amount, sut.getSize());
    }

    void checkArrayAdd(Integer index, T value) {
        Integer originalSize = sut.getSize();
        System.out.println("Original size: " + originalSize);
        sut.add(index, value);

        assertEquals(value, sut.get(index));
        assertEquals(originalSize + 1, sut.getSize());

        for (int i = 0; i < originalSize; i++) {
            if (i < index) {
                assertEquals(i, sut.get(i), "Element mismatch at index " + i);
            } else {
                assertEquals(i, sut.get(i + 1), "Element mismatch at index " + (i + 1));
            }
        }
    }

    void checkArrayRemove(Integer index) {
        Integer originalSize = sut.getSize();
        T removedElement = sut.get(index);
        System.out.println("Original size: " + originalSize + ", Removing element at index: " + index);
        sut.remove(index);
        assertEquals(originalSize - 1, sut.getSize());

        for (int i = 0; i < sut.getSize(); i++) {
            if (i < index) {
                assertEquals(i, sut.get(i), "Element mismatch at index " + i);
            } else {
                assertEquals(i + 1, sut.get(i), "Element mismatch at index " + (i + 1));
            }
        }

        assertThrows(IndexOutOfBoundsException.class, () -> sut.get(originalSize - 1), "Expected exception not thrown for index " + (originalSize - 1));
    }

    @Test
    void should_add_one_number_to_list() {
        sut.add((T) (Integer) 10);
        assertEquals(10, sut.get(0));
        assertEquals(1, sut.getSize());
    }

    @Test
    void should_add_one_string_to_list() {
        sut.add((T) "hello");
        assertEquals("hello", sut.get(0));
        assertEquals(1, sut.getSize());
    }

    @Test
    void should_add_11_items_to_list() {
        populate(11);
        for (int i = 0; i < 11; i++) {
            assertEquals(i, sut.get(i));
        }
    }

    @Test
    void should_add_item_to_middle_with_under_10_elements() {
        populate(9);
        checkArrayAdd(5, (T) (Integer) 19);
    }

    @Test
    void should_add_null() {
        sut.add(null);
        assertEquals(1, sut.getSize());
    }

    @Nested
    class PrePopulatedTests {

        @BeforeEach
        void init() {
            populate(15);
        }

        @Test
        void should_add_item_to_middle_with_over_10_elements() {
            checkArrayAdd(7, (T) (Integer) 22);
        }

        @Test
        void should_add_item_to_beginning_of_list() {
            checkArrayAdd(0, (T) (Integer) 22);
        }

        @Test
        void should_add_item_to_end_of_list() {
            checkArrayAdd(sut.getSize(), (T) (Integer) 22);
        }

        @Test
        void should_add_multiple_indexed_items_to_list() {
            Integer originalSize = sut.getSize();

            sut.add(7, (T) (Integer) 22);
            sut.add(11, (T) (Integer) 99);

            assertEquals(22, sut.get(7));
            assertEquals(99, sut.get(11));
            assertEquals(originalSize + 2, sut.getSize());
        }

        @Test
        void should_remove_item_from_beginning_of_list() {
            checkArrayRemove(0);
        }

        @Test
        void should_remove_item_from_middle_of_list() {
            checkArrayRemove(6);
        }

        @Test
        void should_remove_item_from_end_of_list() {
            Integer originalSize = sut.getSize();
            assertEquals((Integer) (originalSize - 1), sut.get(originalSize - 1));
            sut.remove(originalSize - 1);
            assertEquals(originalSize - 1, sut.getSize());
        }

        @Test
        void should_remove_multiple_items_from_list() {
            Integer originalSize = sut.getSize();
            sut.remove(7);
            sut.remove(8);
            assertEquals(originalSize - 2, sut.getSize());
        }

        @Test
        void should_add_null_at_index() {
            sut.add(7, null);
            assertEquals(16, sut.getSize());
        }
    }

    @Test
    void should_throw_index_out_of_bounds_exception() {
        assertThrows(IndexOutOfBoundsException.class, () -> sut.add(80, (T) "error"));
        assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(80));
    }
}
