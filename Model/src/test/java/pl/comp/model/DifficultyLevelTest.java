package pl.comp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void easyTest() {
        var easyLevel= DifficultyLevel.Easy;
        assertEquals(20, easyLevel.getFieldsToDelete());
    }

    @Test
    void normalTest() {
        var normalLevel= DifficultyLevel.Normal;
        assertEquals(35, normalLevel.getFieldsToDelete());
    }

    @Test
    void hardTest() {
        var hardLevel= DifficultyLevel.Hard;
        assertEquals(50, hardLevel.getFieldsToDelete());
    }
}