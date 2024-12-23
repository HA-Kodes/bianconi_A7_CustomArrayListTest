package com.coderscampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomArrayListTest {

    private CustomArrayList<Integer> customList;

    /*
    Not necessary, but helps helps to ensure that each test starts with a clean slate, reducing the risk of tests affecting each other.
    Benefits of @BeforeEach
    Isolation: Ensures each test is independent.
    Readability: Makes it clear what setup is needed.
    Reuse: Reuses common setup code across multiple tests.
    */

    @BeforeEach
    void setUp() {
        customList = new CustomArrayList<>();
    }

    @Test
    void testAdd() {
        assertTrue(customList.add(1)); // Add an item and check if it returns true
        assertEquals(1, customList.getSize()); // Check if size is updated correctly
        assertEquals(1, customList.get(0)); // Verify the item is added at the correct index
    }

    @Test
        // Purpose: Ensure the add(int index, T item) method throws IndexOutOfBoundsException for invalid index.
    void testAddWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> customList.add(1, 1));
    }

    @Test
    void testGetSize() { // Purpose: Verify the getSize() method returns the correct size.
        assertEquals(0, customList.getSize()); // Initial size should be 0
        customList.add(1); // Add an item
        assertEquals(1, customList.getSize()); // Check if size is updated
    }

    @Test
        // Purpose: Ensure the get(int index) method returns the correct item.
    void testGet() {
        customList.add(1); // Add an item
        assertEquals(1, customList.get(0)); // Verify the item at index 0
    }

    @Test
        // Purpose: Ensure the get(int index) method throws IndexOutOfBoundsException for invalid index.
    void testGetWithInvalidIndex() {
        customList.add(1); // Add an item
        assertThrows(IndexOutOfBoundsException.class, () -> customList.get(1)); // check for exception.
    }

    @Test
    void testRemove() { // Purpose: Ensure the remove(int index) method removes the item correctly.
        customList.add(1);
        customList.add(2);
        assertEquals(1, customList.remove(0)); // Remove item and verify the returned item
        assertEquals(1, customList.getSize()); // Check if size is updated
        assertEquals(2, customList.get(0)); // Verify the remaining item
    }

    @Test
    void testRemoveWithInvalidIndex() { // Purpose: Ensure the remove(int index) method throws IndexOutOfBoundsException for invalid index.
        customList.add(1);  // Add an item.
        assertThrows(IndexOutOfBoundsException.class, () -> customList.remove(1));  // Check for exception.
    }

    @Test
    void testDoubleSizeOfBackingArray() { // Purpose: Ensure the array doubles in size when full.
        for (int i = 0; i < 11; i++) {
            customList.add(i); // Fill the array and Add 1 more to trigger resizing.
        }
        assertEquals(11, customList.getSize()); // verify the size is correct.
        assertEquals(10, customList.get(10));  // Verify the last item added.
    }

    // Additional Tests
    @Test // Purpose: Ensure the array doubles in size when full using the add(int index, T item) method.
    void testAddToFullArray() { // Purpose: Ensure the array doubles in size when full using the add(int index, T item) method.
        for (int i = 0; i < 10; i++) {
            customList.add(i);  // Fill the array
        }
        customList.add(10);  // Add an item at the end to trigger resizing
        assertEquals(11, customList.getSize()); // Verify the size is correct
        assertEquals(10, customList.get(10));   // Verify the last item added
    }

    @Test
    void testAddAtIndexZero() {
        customList.add(0, 1);
        assertEquals(1, customList.get(0));
        customList.add(0, 2);
        assertEquals(2, customList.get(0));
        assertEquals(1, customList.get(1));
    }

    @Test
    void testRemoveFromMiddle() {
        customList.add(1);
        customList.add(2);
        customList.add(3);
        assertEquals(2, customList.remove(1));
        assertEquals(2, customList.getSize());
        assertEquals(3, customList.get(1));
    }

    @Test
    void testRemoveLastElement() {
        customList.add(1);
        customList.add(2);
        customList.add(3);
        assertEquals(3, customList.remove(2));  // Removing the last element
        assertEquals(2, customList.getSize());
        assertThrows(IndexOutOfBoundsException.class, () -> customList.get(2));
    }

    @Test
    void testAddAtCapacityEdge() {
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        customList.add(10);  // This should trigger capacity increase
        assertEquals(11, customList.getSize());
        assertEquals(10, customList.get(10));
    }

    @Test
    void testRemoveFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customList.remove(0));
    }

    @Test
    void testGetFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> customList.get(0));
    }

    @Test
    void testAddBeyondCurrentSize() {
        customList.add(0, 1); // Valid add
        assertThrows(IndexOutOfBoundsException.class, () -> customList.add(3, 2)); // Invalid add
    }

    @Test
    void testMultipleAddsAndRemoves() {
        customList.add(1);
        customList.add(2);
        customList.add(3);
        customList.remove(1); // Remove element in the middle
        customList.add(1, 4); // Add element back in the middle
        assertEquals(4, customList.get(1));
        assertEquals(3, customList.get(2));
    }

    // Additional Test Cases for Edge Scenarios
    @Test  // Purpose: Ensure the add(int index, T item) method throws IndexOutOfBoundsException for negative index.
    void testAddWithNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> customList.add(-1, 1)); // Check for exception
    }

    @Test
    void testAddWhenArrayIsFull() {  // Ensures resizing when adding an item at full capicity
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        customList.add(10);
        assertEquals(11, customList.getSize());
        assertEquals(10, customList.get(10));
    }

    @Test // Purpose: Ensure the get(int index) method throws IndexOutOfBoundsException for negative index.
    void testGetWithNegativeIndex() { // Tests getting an item with a negative index.
        assertThrows(IndexOutOfBoundsException.class, () -> customList.get(-1)); // Check for exception
    }

    @Test // Purpose: Ensure the remove(int index) method throws IndexOutOfBoundsException for negative index.
    void testRemoveWithNegativeIndex() { // Tests removing an item with a negative index.
        assertThrows(IndexOutOfBoundsException.class, () -> customList.remove(-1)); // Check for exception
    }

    @Test
    void testAddWhenArrayIsFullUsingIndex() {
        for (int i = 0; i < 10; i++) {
            customList.add(i);
        }
        customList.add(10, 10);  // This should trigger capacity increase
        assertEquals(11, customList.getSize());
        assertEquals(10, customList.get(10));
    }
}
