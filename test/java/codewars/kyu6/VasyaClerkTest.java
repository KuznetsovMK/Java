package codewars.kyu6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VasyaClerkTest {

    @Test
    void tickets() {
        assertEquals("YES", VasyaClerk.Tickets(new int[] {25, 25, 50}));
        assertEquals("NO", VasyaClerk.Tickets(new int []{25, 100}));
    }
}