package pl.comp.model;

import java.util.Random;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.RandomizerException;

public class Randomizer {

    private static final Logger logger = LoggerFactory.getLogger(Randomizer.class);
    private static final ResourceBundle
            resourceBundle = ResourceBundle.getBundle("pl.comp.model.Bundle");

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
            throw new IllegalArgumentException(resourceBundle.getString("max-min"));
        }
        return Math.abs(rand.nextInt()) % (max - min + 1) + min;
    }

}
