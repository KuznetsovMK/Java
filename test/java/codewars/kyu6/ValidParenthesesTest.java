package codewars.kyu6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidParenthesesTest {

    @Test
    void validParentheses() {
        assertTrue(ValidParentheses.validParentheses("()"));
        assertFalse(ValidParentheses.validParentheses("())"));
        assertTrue(ValidParentheses.validParentheses("32423(sgsdg)"));
        assertFalse(ValidParentheses.validParentheses("(dsgdsg))2432"));
        assertTrue(ValidParentheses.validParentheses("adasdasfa"));
        assertTrue(ValidParentheses.validParentheses("(())((()())())"));
        assertFalse(ValidParentheses.validParentheses("(())((()())()("));
        assertFalse(ValidParentheses.validParentheses(")())((()())())"));
        assertFalse(ValidParentheses.validParentheses("())(()"));
    }
}