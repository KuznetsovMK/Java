package codewars.kyu5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaginatingAHugeBookTest {

    @Test
    void pageDigits() {
        assertEquals(4, PaginatingAHugeBook.pageDigits(4));
    }

    @Test
    public void testTwelve() {
        assertEquals(15, PaginatingAHugeBook.pageDigits(12));
    }

    @Test
    public void testOneHundred() {
        assertEquals(192, PaginatingAHugeBook.pageDigits(100));
    }
}