package gb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Task2Test {
    private Task2 task2;


    @BeforeEach
    void setUp() {
        task2 = new Task2();
    }

    @Test
    void lastFour() {
        Assertions.assertArrayEquals(new int[]{2, 3, 1, 7}, task2.lastFour(new int[]{1, 2, 4, 2, 3, 1, 7}));
        Assertions.assertArrayEquals(new int[]{}, task2.lastFour(new int[]{1, 2, 4, 2, 3, 1, 4}));
        Assertions.assertThrows(RuntimeException.class, () -> {
            task2.lastFour(new int[]{});
        });
    }
}