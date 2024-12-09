package com.Multi_User_Application.controllers;


import com.Multi_User_Application.entities.SubscriptionPlan;
import com.Multi_User_Application.sevice.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User', 'Growth_Plan_Subscriber')")
    public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans() {
        return ResponseEntity.ok(subscriptionPlanService.getAllPlans());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User', 'Growth_Plan_Subscriber')")
    public ResponseEntity<SubscriptionPlan> getSubscriptionPlanById(@PathVariable long id) {
        return ResponseEntity.ok(subscriptionPlanService.getPlanById(id));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<SubscriptionPlan> createPlan(@RequestBody SubscriptionPlan subscriptionPlan) {
        SubscriptionPlan createdPlan = subscriptionPlanService.createPlan(subscriptionPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<?> deletePlan(@PathVariable long id) {
        subscriptionPlanService.deletePlan(id);
        return ResponseEntity.ok(Map.of("message", "Plan deleted successfully"));
    }

    @PutMapping("/assign/{userId}/{planId}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<Map<String, Object>> assignPlan(@PathVariable long userId, @PathVariable long planId) {
        SubscriptionPlan assignedPlan = subscriptionPlanService.assignPlanToUser(userId,planId);
        return ResponseEntity.ok(Map.of(
                "message", "Plan assigned successfully",
                "assignedPlan", assignedPlan
        ));
    }


}
