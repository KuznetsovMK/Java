package codewars.kyu5;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfTrailingZerosOfNTest {

    @Test
    void zeros() {
        assertEquals(0, NumberOfTrailingZerosOfN.zeros(0));
        assertEquals(1, NumberOfTrailingZerosOfN.zeros(6));
        assertEquals(2, NumberOfTrailingZerosOfN.zeros(14));
    }
}