package codewars.kyu5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductOfConsecutiveFibNumbersTest {

    @Test
    void productFib() {
        long[] r = new long[]{55, 89, 1};
        assertArrayEquals(r, ProductOfConsecutiveFibNumbers.productFib(4895));
        r = new long[]{89, 144, 0};
        assertArrayEquals(r, ProductOfConsecutiveFibNumbers.productFib(5895));

    }
}