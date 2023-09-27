package com.debitinstruction;

import com.debitinstruction.models.BusinessRules;
import com.debitinstruction.models.DebitInstruction;
import com.debitinstruction.models.DebitInstructionDay;
import com.debitinstruction.models.DebitInstructionUpdate;
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
    public HttpResponse<?> updateDebitInstruction(HttpRequest<?> request, @QueryValue String customerId, @QueryValue String mortgageId, @Body DebitInstructionDay debInstructSelectedDay) {

        DebitInstruction currentInstruction;
        int newSelectedDay = debInstructSelectedDay.getDebInstructSelectedDay();

        try {
            currentInstruction = client.getDebitInstruction(customerId, mortgageId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching current debit instruction data.");
        }

        BusinessRules rules = new BusinessRules();
        DebitInstructionUpdate debitInstructionUpdate = rules.applyBusinessRules(currentInstruction, newSelectedDay);
        try{
            HttpResponse<?> response = client.updateDebitInstruction(customerId, mortgageId,debitInstructionUpdate);
            if (response.getStatus() == HttpStatus.NO_CONTENT){
                return HttpResponse.noContent();
            } else return HttpResponse.serverError("Unexpected response from data-service");
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating current debit instruction data.");
        }
    }

}
