package codewars.kyu6;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YourOrderPleaseTest {

    @Test
    void order() {
//        assertThat(YourOrderPlease.order("is2 Thi1s T4est 3a"), equalTo("Thi1s is2 3a T4est"));
//        assertThat(YourOrderPlease.order("4of Fo1r pe6ople g3ood th5e the2"), equalTo("Fo1r the2 g3ood 4of th5e pe6ople"));
//        assertThat("Empty input should return empty string", YourOrderPlease.order(""), equalTo(""));

        assertEquals("Thi1s is2 3a T4est", YourOrderPlease.order("is2 Thi1s T4est 3a"));
        assertEquals("Fo1r the2 g3ood 4of th5e pe6ople", YourOrderPlease.order("4of Fo1r pe6ople g3ood th5e the2"));
        assertEquals("", YourOrderPlease.order("Empty input should return empty string"));
    }
}