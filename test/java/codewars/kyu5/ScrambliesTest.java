package codewars.kyu5;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScrambliesTest {

    @Test
    void scramble() {
        System.out.println("Fixed Tests scramble");
        assertTrue(Scramblies.scramble("rkqodlw","world"));
        assertTrue(Scramblies.scramble("cedewaraaossoqqyt","codewars"));
        assertFalse(Scramblies.scramble("katas","steak"));
        assertFalse(Scramblies.scramble("scriptjavx","javascript"));
        assertTrue(Scramblies.scramble("scriptingjava","javascript"));
        assertTrue(Scramblies.scramble("scriptsjava","javascripts"));
        assertFalse(Scramblies.scramble("javscripts","javascript"));
        assertTrue(Scramblies.scramble("aabbcamaomsccdd","commas"));
        assertTrue(Scramblies.scramble("commas","commas"));
        assertTrue(Scramblies.scramble("sammoc","commas"));
    }
}