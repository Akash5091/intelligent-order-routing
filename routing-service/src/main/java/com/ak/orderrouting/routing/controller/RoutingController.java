package com.ak.orderrouting.routing.controller;

import com.ak.orderrouting.common.dto.OrderDtos.RoutingEvaluateRequest;
import com.ak.orderrouting.common.dto.OrderDtos.RoutingEvaluateResponse;
import com.ak.orderrouting.routing.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API for routing evaluation.
 */
@RestController
@RequestMapping("/api/routing")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<RoutingEvaluateResponse> evaluate(
            @RequestBody RoutingEvaluateRequest request
    ) {
        return ResponseEntity.ok(routingService.evaluate(request));
    }
}