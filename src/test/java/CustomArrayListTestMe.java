package com.coderscampus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CustomArrayListTestMe<T> {

    private CustomArrayList<T> sut;

    @BeforeEach
    void setUp() {
        sut = new CustomArrayList<>();
    }

    @Nested
    class AddTests {

        @Test
        void testAddElement() {
            sut.add((T) "First");
            assertEquals("First", sut.get(0));
            assertEquals(1, sut.getSize());
        }

        @Test
        void testAddElementWithResize() {
            for (int i = 0; i < 10; i++) {
                sut.add((T) ("Element " + i));
            }
            assertEquals(10, sut.getSize());
            assertEquals(10, ((Object[]) getItemsField(sut)).length);  // Check initial capacity

            sut.add((T) "Element 10");
            assertEquals(11, sut.getSize());
            assertEquals("Element 10", sut.get(10));
            assertEquals(20, ((Object[]) getItemsField(sut)).length);  // Check new capacity after resize
        }

        @Test
        void testAddElementAtValidIndex() {
            sut.add((T) "First");
            sut.add(0, (T) "Zero");
            assertEquals("Zero", sut.get(0));
            assertEquals("First", sut.get(1));
            assertEquals(2, sut.getSize());

            sut.add(1, (T) "Middle");
            assertEquals("Zero", sut.get(0));
            assertEquals("Middle", sut.get(1));
            assertEquals("First", sut.get(2));
            assertEquals(3, sut.getSize());
        }

        @Test
        void testAddElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.add(5, (T) "OutOfBounds"));
            assertTrue(exception.getMessage().contains("Index: 5, Size: 0"));

            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.add(-1, (T) "NegativeIndex"));
            assertTrue(exception.getMessage().contains("Index: -1, Size: 0"));
        }
    }

    @Nested
    class RemoveTests {

        @Test
        void testRemoveElementAtIndex() {
            for (int i = 0; i < 5; i++) {
                sut.add((T) ("Element " + i));
            }
            assertEquals(5, sut.getSize());

            sut.remove(2);
            assertEquals(4, sut.getSize());
            assertEquals("Element 3", sut.get(2));
            assertEquals("Element 4", sut.get(3));

            sut.remove(0);
            assertEquals(3, sut.getSize());
            assertEquals("Element 1", sut.get(0));
        }

        @Test
        void testRemoveElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(10));
            assertTrue(exception.getMessage().contains("Index: 10, Size: 0"));

            sut.add((T) "First");
            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(-1));
            assertTrue(exception.getMessage().contains("Index: -1, Size: 1"));
        }
    }

    @Nested
    class GetTests {

        @Test
        void testGetElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.get(0));
            assertTrue(exception.getMessage().contains("The index, 0, is out of the bounds of the array with size 0"));

            sut.add((T) "First");
            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.get(1));
            assertTrue(exception.getMessage().contains("The index, 1, is out of the bounds of the array with size 1"));
        }

        @Test
        void testGetElementAtIndex() {
            sut.add((T) "First");
            sut.add((T) "Second");
            assertEquals("First", sut.get(0));
            assertEquals("Second", sut.get(1));
        }
    }

    // Helper method to access private fields for testing
    private Object getItemsField(Object obj) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField("items");
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
