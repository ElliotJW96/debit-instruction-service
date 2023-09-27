package com.debitinstruction;

import com.debitinstruction.models.DebitInstruction;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller("/debitinstruction")
public class DebitInstructionController {

    private final DataApiClient client;
    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);

    @Inject
    public DebitInstructionController(DataApiClient client) {
        this.client = client;
    }

    @Get
    public DebitInstruction getDebitInstruction(HttpRequest<?> request, @QueryValue String customerId, @QueryValue String mortgageId){
        try{
            DebitInstruction debitInstruction = client.getDebitInstruction(customerId, mortgageId);
            return debitInstruction;
        } catch (Exception e){
            e.printStackTrace();
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching debit instruction data.");
        }
    }

    @Put
    public void updateDebitInstruction(HttpRequest<?> request){

        //If the customer is updating their selected day of month (and they have already paid this month) then we want to update their "next payment date" to reflect the day change occurring next month.
        //If they have not yet paid this month, their next payment date will not change (and the future next payment date will be updated when the payment is paid as standard)
//        Calendar today = Calendar.getInstance();
//        if(today.get(Calendar.DAY_OF_MONTH) >= debInstructSelectedDay) {
//            Calendar nextMonth = Calendar.getInstance();
//            nextMonth.add(Calendar.MONTH, 1);
//
//            // Check if the debInstructSelectedDay is valid for that month
//            if (debInstructSelectedDay > nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) {
//                nextMonth.set(Calendar.DAY_OF_MONTH, nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH));  // set to last day of the month
//            } else {
//                nextMonth.set(Calendar.DAY_OF_MONTH, debInstructSelectedDay);
//            }
//            Date newPaymentDate = nextMonth.getTime();
//        }

        //update row with new day of month and newPaymentDate (and error handling and log)
    }

}
