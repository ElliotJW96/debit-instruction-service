package com.debitinstruction;

import com.debitinstruction.models.DebitInstruction;
import com.debitinstruction.models.DebitInstructionUpdate;
import jakarta.inject.Singleton;

import java.util.Calendar;
import java.util.Date;

/**
 * Provides business logic for processing and modifying debit instruction data.
 */
@Singleton
public class BusinessRules {

    /**
     * Applies business rules to determine the next payment date based on the selected day of the month.
     *
     * @param currentInstruction The current debit instruction.
     * @param newSelectedDay     The newly selected day of the month for payments.
     * @return The updated debit instruction with the new next payment date.
     */
    public DebitInstructionUpdate applyBusinessRules(DebitInstruction currentInstruction, int newSelectedDay) {
        if (currentInstruction == null || currentInstruction.getDebInstructNextPaymentDate() == null) {
            throw new IllegalArgumentException("Current instruction or its date is null.");
        }

        Date nextPaymentDate = currentInstruction.getDebInstructNextPaymentDate();
        Calendar nextPaymentCal = Calendar.getInstance();
        nextPaymentCal.setTime(nextPaymentDate);

        Calendar today = Calendar.getInstance();

        // If nextPaymentDate is not in the same month as today, adjust the day.
        if (!(nextPaymentCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) && nextPaymentCal.get(Calendar.YEAR) == today.get(Calendar.YEAR))) {
            if (newSelectedDay > nextPaymentCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                nextPaymentCal.set(Calendar.DAY_OF_MONTH, nextPaymentCal.getActualMaximum(Calendar.DAY_OF_MONTH));  // set to last day of the month
            } else {
                nextPaymentCal.set(Calendar.DAY_OF_MONTH, newSelectedDay);
            }
            nextPaymentDate = nextPaymentCal.getTime();
        }

        java.sql.Date sqlDate = new java.sql.Date(nextPaymentDate.getTime());

        DebitInstructionUpdate instructionUpdate = new DebitInstructionUpdate();
        instructionUpdate.setDebInstructSelectedDay(newSelectedDay);
        instructionUpdate.setDebInstructNextPaymentDate(sqlDate);

        return instructionUpdate;
    }
}
