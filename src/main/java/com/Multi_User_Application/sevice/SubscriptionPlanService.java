package com.Multi_User_Application.sevice;

import com.Multi_User_Application.entities.SubscriptionPlan;

import java.util.List;

public interface SubscriptionPlanService {
    List<SubscriptionPlan> getAllPlans();
    SubscriptionPlan getPlanById(Long id);
    SubscriptionPlan createPlan(SubscriptionPlan subscriptionPlan);
    void deletePlan(Long id);
    SubscriptionPlan assignPlanToUser(Long userId, Long planId);
}
