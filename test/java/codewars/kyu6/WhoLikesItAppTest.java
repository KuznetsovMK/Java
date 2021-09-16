package codewars.kyu6;

import codewars.kyu6.WhoLikesItApp;
import junit.framework.TestCase;
import org.junit.Test;

public class WhoLikesItAppTest extends TestCase {
    @Test
    public void test() {
        assertEquals("no one likes this", WhoLikesItApp.whoLikesIt());
        assertEquals("Peter likes this", WhoLikesItApp.whoLikesIt("Peter"));
        assertEquals("Jacob and Alex like this", WhoLikesItApp.whoLikesIt("Jacob", "Alex"));
        assertEquals("Max, John and Mark like this", WhoLikesItApp.whoLikesIt("Max", "John", "Mark"));
        assertEquals("Alex, Jacob and 2 others like this", WhoLikesItApp.whoLikesIt("Alex", "Jacob", "Mark", "Max"));
    }
}