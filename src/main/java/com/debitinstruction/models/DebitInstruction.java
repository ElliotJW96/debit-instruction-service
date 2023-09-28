package com.debitinstruction.models;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.sql.Timestamp;
import java.sql.Date;

@Introspected
@Serdeable
public class DebitInstruction {

    String debInstructId;
    String debInstructMortgageId;
    int debInstructSelectedDay;
    Date debInstructNextPaymentDate;
    String debInstructOriginAccount;
    String debInstructOriginSortCode;
    Timestamp debInstructDate;

    public String getDebInstructId() {
        return debInstructId;
    }

    public void setDebInstructId(String debInstructId) {
        this.debInstructId = debInstructId;
    }

    public String getDebInstructMortgageId() {
        return debInstructMortgageId;
    }

    public void setDebInstructMortgageId(String debInstructMortgageId) {
        this.debInstructMortgageId = debInstructMortgageId;
    }

    public int getDebInstructSelectedDay() {
        return debInstructSelectedDay;
    }

    public void setDebInstructSelectedDay(int debInstructSelectedDay) {
        this.debInstructSelectedDay = debInstructSelectedDay;
    }

    public Date getDebInstructNextPaymentDate() {
        return debInstructNextPaymentDate;
    }

    public void setDebInstructNextPaymentDate(Date debInstructNextPaymentDate) {
        this.debInstructNextPaymentDate = debInstructNextPaymentDate;
    }

    public String getDebInstructOriginAccount() {
        return debInstructOriginAccount;
    }

    public void setDebInstructOriginAccount(String debInstructOriginAccount) {
        this.debInstructOriginAccount = debInstructOriginAccount;
    }

    public String getDebInstructOriginSortCode() {
        return debInstructOriginSortCode;
    }

    public void setDebInstructOriginSortCode(String debInstructOriginSortCode) {
        this.debInstructOriginSortCode = debInstructOriginSortCode;
    }

    public Timestamp getDebInstructDate() {
        return debInstructDate;
    }

    public void setDebInstructDate(Timestamp debInstructDate) {
        this.debInstructDate = debInstructDate;
    }
}
