package pl.comp.model;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Randomizer {

    private final Random rand;
    private static final Logger logger = LoggerFactory.getLogger(Randomizer.class);

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
        try {
            if (max < min) {
                throw new IllegalArgumentException("Max must not be smaller than min!");
            }
        } catch (IllegalArgumentException e) {
            logger.error(e.toString());
            throw e;
        }
        return Math.abs(rand.nextInt()) % (max - min + 1) + min;
    }

}
