package codewars.kyu5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegersRecreationOneTest {

    @Test
    void listSquared() {
        assertEquals("[[1, 1], [42, 2500], [246, 84100]]", IntegersRecreationOne.listSquared(1, 250));
    }
    @Test
    public void test2() {
        assertEquals("[[42, 2500], [246, 84100]]", IntegersRecreationOne.listSquared(42, 250));
    }
    @Test
    public void test3() {
        assertEquals("[[287, 84100]]", IntegersRecreationOne.listSquared(250, 500));
    }
}