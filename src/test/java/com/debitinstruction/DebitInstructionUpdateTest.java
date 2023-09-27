package com.debitinstruction;

import com.debitinstruction.models.DebitInstructionUpdate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DebitInstructionUpdateTest {

    @Test
    public void testSetDebInstructSelectedDay_ValidValue() {
        DebitInstructionUpdate update = new DebitInstructionUpdate();
        update.setDebInstructSelectedDay(15);
        assertEquals(15, update.getDebInstructSelectedDay());
    }

    @Test
    public void testSetDebInstructSelectedDay_InvalidValue() {
        DebitInstructionUpdate update = new DebitInstructionUpdate();
        assertThrows(IllegalArgumentException.class, () -> update.setDebInstructSelectedDay(32));
    }
}

