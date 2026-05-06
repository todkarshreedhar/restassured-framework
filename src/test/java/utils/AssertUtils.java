package utils;

import org.testng.Assert;

public class AssertUtils {
    public static void assertEquals(Object actual, Object expected, String message)
    {
        Assert.assertEquals(actual, expected, message);
    }
    public static void assertNotNull(Object object, String message)
    {
        Assert.assertNotNull(object, message);
    }
    public static void assertStatusCode(int actual, int expected){
        Assert.assertEquals(actual, expected, "Status Code Mismatch → Expected: " + expected + " but got: " + actual);
    }
}
