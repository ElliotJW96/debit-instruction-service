package com.debitinstruction.models;

import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Singleton
public class BusinessRules {
    public DebitInstructionUpdate applyBusinessRules(DebitInstruction currentInstruction, int newSelectedDay) {
        Date nextPaymentDate = currentInstruction.getDebInstructNextPaymentDate();
        Calendar nextPaymentCal = Calendar.getInstance();
        nextPaymentCal.setTime(nextPaymentDate);

        Calendar today = Calendar.getInstance();

        // If nextPaymentDate is in the same month as today, return without changes.
        if (nextPaymentCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                nextPaymentCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            // No changes needed, simply return the DebitInstructionUpdate.
        } else {
            // Set the day of the nextPaymentDate to the newSelectedDay.
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

