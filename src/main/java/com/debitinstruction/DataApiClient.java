package com.debitinstruction;

import com.debitinstruction.models.DebitInstruction;
import com.debitinstruction.models.DebitInstructionUpdate;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Singleton
public class DataApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DataApiClient.class);
    private static final String DATA_SERVICE_URL = "http://host.docker.internal:8082/data/";

    @Client(DATA_SERVICE_URL)
    HttpClient client;

    @Inject
    public DataApiClient(HttpClient client) {
        this.client = client;
    }

    public DebitInstruction getDebitInstruction(String customerId, String mortgageId) {
        try {
            String constructedUrl = DATA_SERVICE_URL  + "debitinstruction?mortgageId="+mortgageId + "&customerId=" +customerId;
            LOG.info("Constructed URL for data-service: " + constructedUrl);
            HttpRequest<?> request = HttpRequest.GET(constructedUrl);
            return client.toBlocking().retrieve(request, DebitInstruction.class);
        } catch (Exception e) {
            LOG.error("Error fetching debit instruction data.", e);
            throw e;
        }
    }
}

