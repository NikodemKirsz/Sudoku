package pl.comp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomizerTest {

    @Test
    void getRandomIntTest() {
        var rand = new Randomizer();

        for (int i = 0; i < 5; i++) {
            int valBetween0and81 = rand.getRandomInt();
            assertTrue(valBetween0and81 <= 81);
            assertTrue(valBetween0and81 >= 0);
        }

        for (int i = 0; i < 5; i++) {
            int valBetween0and10 = rand.getRandomInt(10);
            assertTrue(valBetween0and10 <= 10);
            assertTrue(valBetween0and10 >= 0);
        }

        for (int i = 0; i < 5; i++) {
            int valBetween5and10 = rand.getRandomInt(10, 5);
            assertTrue(valBetween5and10 <= 10);
            assertTrue(valBetween5and10 >= 5);
        }

        for (int i = 0; i < 5; i++) {
            int valEqual7 = rand.getRandomInt(7, 7);
            assertEquals(7, valEqual7);
        }

        assertThrows(IllegalArgumentException.class, ()->rand.getRandomInt(5, 10));
    }
}