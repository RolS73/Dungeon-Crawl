package com.codecool.dungeoncrawl.logic;
import java.util.Random;
public class RandomHelper {

    private static final Random RANDOM = new Random();

    private RandomHelper() {
    }

    public static int nextInt(int upper) {
        return RANDOM.nextInt(upper);
    }

    public static String chooseOne(String[] possibilities) {
        if (possibilities == null || possibilities.length < 1) {
            final String msg =
                    "Possibilities should be a non-empty array of strings.";
            throw new IllegalArgumentException(msg);
        }

        return possibilities[nextInt(possibilities.length)];
    }

    public static int nextInt(int lower, int upper) {
        return lower + nextInt(upper - lower);
    }

    public static boolean eventWithChance(int chance) {
        return RANDOM.nextInt(100) < chance;
    }


}

