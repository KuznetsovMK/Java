package codewars.kyu5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplePigLatinTest {

    @Test
    void pigIt() {
        assertEquals("igPay atinlay siay oolcay", SimplePigLatin.pigIt("Pig latin is cool"));
        assertEquals("hisTay siay ymay tringsay", SimplePigLatin.pigIt("This is my string"));
        assertEquals("elloHay orldway !", SimplePigLatin.pigIt("Hello world !"));
        assertEquals("elloHay aay orldway !", SimplePigLatin.pigIt("Hello a world !"));
        assertEquals("elloHay aay orldway ?", SimplePigLatin.pigIt("Hello a world ?"));
        assertEquals("", SimplePigLatin.pigIt(""));
    }
}