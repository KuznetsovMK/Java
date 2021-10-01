package codewars.kyu5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuddyPairsTest {

    @Test
    void buddy() {
        assertEquals("(1081184 1331967)", BuddyPairs.buddy(1071625, 1103735));
        assertEquals("Nothing", BuddyPairs.buddy(2382, 3679));
        assertEquals("(1050 1925)", BuddyPairs.buddy(381, 4318));
    }
}