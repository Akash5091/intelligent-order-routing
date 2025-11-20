package com.ak.orderrouting.order.service;

import com.ak.orderrouting.common.dto.OrderDtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * REST client for communicating with routing-service.
 */
@Component
public class RoutingClient {

    private final RestClient restClient;
    private final String routingBaseUrl;

    public RoutingClient(
            @Value("${routing-service.base-url:http://localhost:8083}") String routingBaseUrl
    ) {
        this.routingBaseUrl = routingBaseUrl;
        this.restClient = RestClient.create();
    }

    public RoutingEvaluateResponse evaluate(RoutingEvaluateRequest request) {
        return restClient.post()
                .uri(routingBaseUrl + "/api/routing/evaluate")
                .body(request)
                .retrieve()
                .body(RoutingEvaluateResponse.class);
    }
}