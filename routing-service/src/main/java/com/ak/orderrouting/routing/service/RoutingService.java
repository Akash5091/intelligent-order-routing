package com.ak.orderrouting.routing.service;

import com.ak.orderrouting.common.dto.OrderDtos.*;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Service for evaluating optimal warehouse routing decisions.
 * Currently implements a simple scoring algorithm.
 * Future enhancements: integrate with inventory service, implement ML-based predictions.
 */
@Service
public class RoutingService {

    private final Random random = new Random();

    public RoutingEvaluateResponse evaluate(RoutingEvaluateRequest req) {
        // For now we return a mock decision.
        // In production, this would:
        // 1. Query warehouse-service for all warehouses
        // 2. Query inventory-service for each warehouse's stock
        // 3. Calculate geographic distance (Haversine formula)
        // 4. Score each warehouse based on:
        //    - Distance (lower is better)
        //    - Handling cost (lower is better)
        //    - Inventory availability (higher is better)
        //    - SLA feasibility (must meet delivery window)
        // 5. Select warehouse with highest score
        
        var decision = new RoutingDecision(
                1L,
                "WH-001",
                10.5,  // distance in km
                1.25,  // handling cost
                4.99,  // shipping cost estimate
                true,  // SLA met
                0.85   // total score (0-1)
        );
        return new RoutingEvaluateResponse(req.orderId(), decision);
    }
}