package gb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task3Test {
    private Task3 task3;


    @BeforeEach
    void setUp() {
        task3 = new Task3();
    }

    @Test
    void checkArray() {
        assertTrue(task3.checkArray(new int[]{1, 1, 4, 4, 4, 1, 1}));
        assertTrue(task3.checkArray(new int[]{4, 4, 1, 1}));
        assertFalse(task3.checkArray(new int[]{4, 4, 4, 4, 4}));
        assertFalse(task3.checkArray(new int[]{1, 1, 1, 1}));
        assertFalse(task3.checkArray(new int[]{1, 3, 4}));
    }
}