package com.debitinstruction.models;

import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Singleton
public class BusinessRules {
    public DebitInstructionUpdate applyBusinessRules(DebitInstruction currentInstruction, int newSelectedDay) {


        //Initialise a nextPaymentDate as the current date in the database.
        LocalDate nextPaymentDate = currentInstruction.getDebInstructNextPaymentDate();

        //If the customer is updating their selected day of month (and they have already paid this month) then we want to update their "next payment date" to reflect the day change occurring next month.
        //If they have not yet paid this month, their next payment date will not change (and the future next payment date will be updated when the payment is paid as standard)
        Calendar today = Calendar.getInstance();

        if (today.get(Calendar.DAY_OF_MONTH) >= newSelectedDay) { //if you've paid this month already
            Calendar nextMonth = Calendar.getInstance();
            nextMonth.add(Calendar.MONTH, 1);

            // Check if the debInstructSelectedDay is valid for that month
            if (newSelectedDay > nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH));  // set to last day of the month
            } else {
                nextMonth.set(Calendar.DAY_OF_MONTH, newSelectedDay);
            }
            //set nextPaymentDate to the modified value
            nextPaymentDate = nextMonth.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        DebitInstructionUpdate instructionUpdate = new DebitInstructionUpdate();
        instructionUpdate.setDebInstructSelectedDay(newSelectedDay);
        instructionUpdate.setDebInstructNextPaymentDate(nextPaymentDate);
        return instructionUpdate;
    }
}
