package codewars.kyu6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TakeATenMinuteWalkTest {

    @Test
    void isValid() {
        assertTrue(TakeATenMinuteWalk.isValid(new char[] {'n','s','n','s','n','s','n','s','n','s'}));
        assertFalse(TakeATenMinuteWalk.isValid(new char[] {'w','e','w','e','w','e','w','e','w','e','w','e'}));
        assertFalse(TakeATenMinuteWalk.isValid(new char[] {'w'}));
        assertFalse( TakeATenMinuteWalk.isValid(new char[] {'n','n','n','s','n','s','n','s','n','s'}));
    }
}