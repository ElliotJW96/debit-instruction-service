package com.debitinstruction.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.sql.Date;

@Introspected
@Serdeable.Deserializable
@Serdeable.Serializable
public class DebitInstructionUpdate {

    private int debInstructSelectedDay;
    private Date debInstructNextPaymentDate;

    public int getDebInstructSelectedDay() {
        return debInstructSelectedDay;
    }

    public void setDebInstructSelectedDay(int debInstructSelectedDay) {
        if (debInstructSelectedDay < 1 || debInstructSelectedDay > 31) {
            throw new IllegalArgumentException("The day value must be between 1 and 31.");
        }
        this.debInstructSelectedDay = debInstructSelectedDay;
    }

    public Date getDebInstructNextPaymentDate() {
        return debInstructNextPaymentDate;
    }

    public void setDebInstructNextPaymentDate(Date debInstructNextPaymentDate) {
        this.debInstructNextPaymentDate = debInstructNextPaymentDate;
    }
}
