package com.debitinstruction.models;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
public class DebitInstructionUpdate {

    private int debInstructSelectedDay;
    private LocalDate debInstructNextPaymentDate;

    public int getDebInstructSelectedDay() {
        return debInstructSelectedDay;
    }
    public void setDebInstructSelectedDay(int debInstructSelectedDay) {
        this.debInstructSelectedDay = debInstructSelectedDay;
    }

    public LocalDate getDebInstructNextPaymentDate() {
        return debInstructNextPaymentDate;
    }
    public void setDebInstructNextPaymentDate(LocalDate debInstructNextPaymentDate) {
        this.debInstructNextPaymentDate = debInstructNextPaymentDate;
    }

    @Override
    public String toString() {
        return "DebitInstructionUpdate{" +
                "debInstructSelectedDay=" + debInstructSelectedDay +
                ", debInstructNextPaymentDate=" + debInstructNextPaymentDate +
                '}';
    }
}

