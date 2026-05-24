package com.gameengine;

import com.gameengine.validator.EntityValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityValidatorTest {

    @Test
    void validIdIsAccepted() {
        assertTrue(EntityValidator.isValidId("enemy_001"));
        assertTrue(EntityValidator.isValidId("player1"));
    }

    @Test
    void blankOrNullIdIsRejected() {
        assertFalse(EntityValidator.isValidId(null));
        assertFalse(EntityValidator.isValidId("  "));
        assertFalse(EntityValidator.isValidId(""));
    }

    @Test
    void invalidIdCharactersAreRejected() {
        assertFalse(EntityValidator.isValidId("bad id!"));
        assertFalse(EntityValidator.isValidId("bad/id"));
    }

    @Test
    void healthBoundsAreEnforced() {
        assertTrue(EntityValidator.isValidHealth(1));
        assertTrue(EntityValidator.isValidHealth(10000));
        assertFalse(EntityValidator.isValidHealth(0));
        assertFalse(EntityValidator.isValidHealth(10001));
    }

    @Test
    void coordinateBoundsAreEnforced() {
        assertTrue(EntityValidator.isValidCoordinate(0.0));
        assertTrue(EntityValidator.isValidCoordinate(-10000.0));
        assertFalse(EntityValidator.isValidCoordinate(Double.NaN));
        assertFalse(EntityValidator.isValidCoordinate(Double.POSITIVE_INFINITY));
        assertFalse(EntityValidator.isValidCoordinate(99999.0));
    }
}
