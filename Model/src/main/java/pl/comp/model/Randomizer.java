package pl.comp.model;

import java.util.Random;

public class Randomizer {

    private final Random rand;

    public Randomizer() {
        rand = new Random();
    }

    public int getRandomInt() {
        return getRandomInt(81);
    }

    public int getRandomInt(int max) {
        return getRandomInt(max, 0);
    }

    public int getRandomInt(int max, int min) {
        if (max < min) {
            throw new IllegalArgumentException("Max must not be smaller than min!");
        }

        return Math.abs(rand.nextInt()) % (max - min + 1) + min;
    }

}
