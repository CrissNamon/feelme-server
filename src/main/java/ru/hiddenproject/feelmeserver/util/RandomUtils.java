package ru.hiddenproject.feelmeserver.util;

import java.util.Random;

/**
 * Utils for some operations with random data
 */
public class RandomUtils {

    /**
     * Generates random string of {@code length}
     * @param length String length
     * @return Random string
     */
    public static String generateRandomString(int length)
    {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

}
