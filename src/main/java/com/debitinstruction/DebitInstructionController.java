package com.debitinstruction;

import com.debitinstruction.models.DebitInstruction;
import com.debitinstruction.models.DebitInstructionDay;
import com.debitinstruction.models.DebitInstructionUpdate;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller providing endpoints for operations related to debit instructions.
 */
@Controller("/debitinstruction")
public class DebitInstructionController {

    private final DataApiClient client;
    private final BusinessRules businessRules;
    private static final Logger LOG = LoggerFactory.getLogger(DebitInstructionController.class);

    /**
     * Constructor to initialize required dependencies.
     *
     * @param client The Data API client.
     * @param businessRules Business rules handler.
     */
    @Inject
    public DebitInstructionController(DataApiClient client, BusinessRules businessRules) {
        this.client = client;
        this.businessRules = businessRules;
    }

    /**
     * Fetches the debit instruction associated with given customer and mortgage IDs.
     *
     * @param request The HTTP request.
     * @param customerId The ID of the customer.
     * @param mortgageId The ID of the mortgage.
     * @return The requested debit instruction.
     */
    @Get
    public DebitInstruction getDebitInstruction(HttpRequest<?> request, @QueryValue String customerId, @QueryValue String mortgageId) {
        try {
            return client.getDebitInstruction(customerId, mortgageId);
        } catch (Exception e) {
            LOG.error("Error fetching debit instruction data.", e);
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching debit instruction data.");
        }
    }

    /**
     * Updates the debit instruction associated with given customer and mortgage IDs.
     *
     * @param request The HTTP request.
     * @param customerId The ID of the customer.
     * @param mortgageId The ID of the mortgage.
     * @param debInstructSelectedDay The day of the month for the debit instruction.
     * @return The HTTP response after the update operation.
     */
    @Put
    public HttpResponse<?> updateDebitInstruction(HttpRequest<?> request, @QueryValue String customerId, @QueryValue String mortgageId, @Body DebitInstructionDay debInstructSelectedDay) {
        DebitInstruction currentInstruction;
        try {
            currentInstruction = client.getDebitInstruction(customerId, mortgageId);
        } catch (Exception e) {
            LOG.error("Error fetching current debit instruction data.", e);
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching current debit instruction data.");
        }

        DebitInstructionUpdate debitInstructionUpdate = businessRules.applyBusinessRules(currentInstruction, debInstructSelectedDay.getDebInstructSelectedDay());
        try {
            HttpResponse<?> response = client.updateDebitInstruction(customerId, mortgageId, debitInstructionUpdate);
            if (response.getStatus().getCode() / 100 == 2) { // Check for 2xx status codes
                return HttpResponse.noContent();
            } else {
                return HttpResponse.serverError("Unexpected response from data-service");
            }
        } catch (Exception e) {
            LOG.error("Error updating current debit instruction data.", e);
            throw new HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating current debit instruction data.");
        }
    }
}
