package com.coderscampus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coderscampus.CustomArrayList;
import com.coderscampus.CustomList;

@SuppressWarnings("unchecked")
public class CustomArrayListTest {

    // Test-Driven Development Methodology
    // Step 1 - Write a failing test
    // Step 2 - Write the business logic to make the test pass
    // Step 3 - Refactor lines of code
    // Step 4 - repeat

    // Three A's
    // Arrange, Act, Assert

    // Arrange
    private CustomList<Integer> sut;
    private Integer index;

    @BeforeEach
    void init() {
        sut = new CustomArrayList<>();
    }

    // Act
    void populate(Integer amount) {
        for (Integer i = 0; i < amount; i++) {
            Integer index = null;
            sut.add(index, i);
        }
        assertEquals(amount, sut.getSize());
    }

    // Act
    void checkArrayAdd(Integer index, Integer value) {
        Integer originalSize = sut.getSize();
        sut.add(index, value);

        for (int i = 0; i < sut.getSize(); i++) {
            if (i < index) {
                assertEquals(i, sut.get(i));
            } else if (i == index) {
                assertEquals(sut.get(i), value);
            } else {
                assertEquals(sut.get(i), i - 1);
            }
        }

        assertEquals(originalSize + 1, sut.getSize());
    }

    void checkArrayRemove(Integer index) {
        Integer originalSize = sut.getSize();
        assertEquals(sut.get(index), index);
        sut.remove(index);
        assertEquals(sut.get(index), index + 1);
        assertEquals(sut.getSize(), originalSize - 1);
    }

    @Test
    void should_add_one_number_to_list() {
        sut.add(10);
        assertEquals(10, sut.get(0));
        assertEquals(1, sut.getSize());
    }

    @Test
    void should_add_one_string_to_list() {
        sut.add(20);
        assertEquals(20, sut.get(0));
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
    void should_add_item_to_middle_with_under_20_elements() {
        populate(5);
        checkArrayAdd(5, 19);
    }

    @Test
    void should_add_null() {
        sut.add(index, null);
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
            checkArrayAdd(7, 21);
        }

        @Test
        void should_add_item_to_beginning_of_list() {
//            checkArrayAdd(0, (T) (Integer) 22);
            sut.add(0);
        }

        @Test
        void should_add_item_to_end_of_list() {
            checkArrayAdd(sut.getSize(), 22);
        }

        @Test
        void should_add_multiple_indexed_items_to_list() {
            Integer originalSize = sut.getSize();
            sut.add((int) 7, 22);
            sut.add(11, 99);

            assertEquals(sut.get(7), 22);
            assertEquals(sut.get(11), 99);
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
            assertEquals(sut.get(originalSize - 1), originalSize - 1);
            sut.remove(originalSize - 1);
            assertEquals(sut.getSize(), originalSize - 1);
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
        assertThrows(IndexOutOfBoundsException.class, () -> sut.add(80,  100));
        assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(80));
    }
}
