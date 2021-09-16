package codewars.kyu6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateEncoderTest {

    @Test
    void encode() {
        assertEquals(")()())()(()()(",
                DuplicateEncoder.encode("Prespecialized"));
        assertEquals("))))())))",DuplicateEncoder.encode("   ()(   "));
        assertEquals("((",DuplicateEncoder.encode("Jk"));
        assertEquals("())",DuplicateEncoder.encode("JkK"));
        assertEquals("())))",DuplicateEncoder.encode("J k K"));
        assertEquals("((()()",DuplicateEncoder.encode(" J-k+K"));
    }
}