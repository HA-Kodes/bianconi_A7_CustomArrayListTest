package com.coderscampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {

    private CustomArrayList<String> sut;

    @BeforeEach
    void setUp() {
        sut = new CustomArrayList<>();
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

    @Nested
    class AddTests {

        @Test
        void testAddElement() {
            sut.add("First");
            assertEquals("First", sut.get(0));
            assertEquals(1, sut.getSize("Test message"));
        }

        @Test
        void testAddElementWithResize() {
            for (int i = 0; i < 10; i++) {
                sut.add("Element " + i);
            }
            assertEquals(10, sut.getSize("Test message"));
            sut.add("Element 10");
            assertEquals(11, sut.getSize("Test message"));
            assertEquals("Element 10", sut.get(10));
        }

        @Test
        void testAddElementAtValidIndex() {
            sut.add("First");
            sut.add(0, "Zero");
            assertEquals("Zero", sut.get(0));
            assertEquals("First", sut.get(1));
            assertEquals(2, sut.getSize("Test message"));

            sut.add(1, "Middle");
            assertEquals("Zero", sut.get(0));
            assertEquals("Middle", sut.get(1));
            assertEquals("First", sut.get(2));
            assertEquals(3, sut.getSize("Test message"));
        }

        @Test
        void testAddElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.add(5, "OutOfBounds"));
            assertTrue(exception.getMessage().contains("Index: 5, Size: 0"));

            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.add(-1, "NegativeIndex"));
            assertTrue(exception.getMessage().contains("Index: -1, Size: 0"));
        }
    }

    @Nested
    class RemoveTests {

        @Test
        void testRemoveElementAtIndex() {
            for (int i = 0; i < 5; i++) {
                sut.add("Element " + i);
            }
            assertEquals(5, sut.getSize("Test message"));

            assertEquals("Element 2", sut.remove(2));
            assertEquals(4, sut.getSize("Test message"));
            assertEquals("Element 3", sut.get(2));
            assertEquals("Element 4", sut.get(3));

            assertEquals("Element 0", sut.remove(0));
            assertEquals(3, sut.getSize("Test message"));
            assertEquals("Element 1", sut.get(0));
        }

        @Test
        void testRemoveElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(10));
            assertTrue(exception.getMessage().contains("Index: 10, Size: 0"));

            sut.add("First");
            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.remove(-1));
            assertTrue(exception.getMessage().contains("Index: -1, Size: 1"));
        }

        @Test
        void should_remove_from_list_end() {
            for (int i = 0; i < 10; i++) {
                sut.add("item " + i);
            }

            assertEquals("item 9", sut.remove(9)); // Removing the last element
            assertEquals(9, sut.getSize("Test message"));

            assertEquals("item 8", sut.remove(8)); // Removing the second last element
            assertEquals(8, sut.getSize("Test message"));
            assertEquals("item 7", sut.get(7)); // Check the element before the one removed
        }

        @Test
        void should_remove_from_list_middle() {
            for (int i = 0; i < 10; i++) {
                sut.add("item " + i);
            }

            assertEquals("item 4", sut.remove(4)); // Removing the middle element
            assertEquals(9, sut.getSize("Test message"));
            assertEquals("item 5", sut.get(4)); // The element after the removed one should move to the removed one's place

            // Check that all other elements are correctly positioned
            assertEquals("item 0", sut.get(0));
            assertEquals("item 1", sut.get(1));
            assertEquals("item 2", sut.get(2));
            assertEquals("item 3", sut.get(3));
            assertEquals("item 6", sut.get(5));
            assertEquals("item 7", sut.get(6));
            assertEquals("item 8", sut.get(7));
            assertEquals("item 9", sut.get(8));
        }
    }

    @Nested
    class GetTests {

        @Test
        void testGetElementAtIndexOutOfBounds() {
            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.get(0));
            assertTrue(exception.getMessage().contains("The index, 0, is out of the bounds of the array with size 0"));

            sut.add("First");
            exception = assertThrows(IndexOutOfBoundsException.class, () -> sut.get(1));
            assertTrue(exception.getMessage().contains("The index, 1, is out of the bounds of the array with size 1"));
        }

        @Test
        void testGetElementAtIndex() {
            sut.add("First");
            sut.add("Second");
            assertEquals("First", sut.get(0));
            assertEquals("Second", sut.get(1));
        }
    }

    @Nested
    class MiscellaneousTests {

        @Test
        void testDoubleSizeOfBackingArray() {
            for (int i = 0; i < 10; i++) {
                sut.add("Element " + i);
            }
            assertEquals(10, sut.getSize("Test message"));
            Object[] doubledArray = sut.doubleSizeOfBackingArray();
            assertEquals(20, doubledArray.length);
        }

        @Test
        void testPrintArrayState() {
            sut.add("First");
            sut.add("Second");
            // Assuming a printArrayState method that prints the state of the array
            sut.getSize("Test message");
        }
    }
}