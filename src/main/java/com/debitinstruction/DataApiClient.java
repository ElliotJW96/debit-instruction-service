package com.debitinstruction;

import com.debitinstruction.models.DebitInstruction;
import com.debitinstruction.models.DebitInstructionUpdate;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client for interacting with the data service to fetch debit instruction-related information.
 */
@Singleton
public class DataApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);
    private static final String DATA_SERVICE_URL = "http://host.docker.internal:8082/data";

    private HttpClient client;

    /**
     * Constructor that initializes the HTTP client.
     *
     * @param client The HTTP client for making requests to the data service.
     */
    @Inject
    public DataApiClient(HttpClient client) {
        this.client = client;
    }

    public DebitInstruction getDebitInstruction(String customerId, String mortgageId) {
        try {
            String constructedUrl = DATA_SERVICE_URL  + "/debitinstruction?mortgageId=" + mortgageId + "&customerId=" + customerId;
            LOG.info("Constructed URL for data-service: {}", constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);
            return client.toBlocking().retrieve(request, DebitInstruction.class);
        } catch (Exception e) {
            LOG.error("Error fetching debit instruction data.", e);
            throw e;
        }
    }

    public HttpResponse<?> updateDebitInstruction(String customerId, String mortgageId, DebitInstructionUpdate debitInstructionUpdate) {
        try {
            String constructedUrl = DATA_SERVICE_URL  + "/debitinstruction?mortgageId=" + mortgageId + "&customerId=" + customerId;
            LOG.info("Constructed URL for data-service: {}", constructedUrl);
            HttpRequest<?> request = HttpRequest.PUT(constructedUrl, debitInstructionUpdate);
            LOG.info("HTTP Method: {}", request.getMethod());
            LOG.info("URL: {}", request.getUri());
            LOG.info("Body: {}", request.getBody().toString());
            LOG.info("debInstructNextPaymentDate: {}", debitInstructionUpdate.getDebInstructNextPaymentDate());

            return client.toBlocking().exchange(request);
        } catch (Exception e) {
            LOG.error("Error updating debit instruction data.", e);
            throw e;
        }
    }
}
