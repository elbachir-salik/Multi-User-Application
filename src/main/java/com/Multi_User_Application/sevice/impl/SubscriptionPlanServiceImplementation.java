package com.Multi_User_Application.sevice.impl;

import com.Multi_User_Application.entities.SubscriptionPlan;
import com.Multi_User_Application.entities.User;
import com.Multi_User_Application.exceptions.SubscriptionPlanNotFoundException;
import com.Multi_User_Application.exceptions.UserNotFoundException;
import com.Multi_User_Application.repo.SubscriptionPlanRepository;
import com.Multi_User_Application.sevice.SubscriptionPlanService;
import com.Multi_User_Application.sevice.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubscriptionPlanServiceImplementation implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserService userService;

    public SubscriptionPlanServiceImplementation(SubscriptionPlanRepository subscriptionPlanRepository, UserService userService) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.userService = userService;
    }

    @Override
    public List<SubscriptionPlan> getAllPlans(){
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public SubscriptionPlan getPlanById(Long id) throws SubscriptionPlanNotFoundException {
        return subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new SubscriptionPlanNotFoundException("SubscriptionPlan with id " + id + " not found"));
    }

    @Override
    public SubscriptionPlan createPlan(SubscriptionPlan subscriptionPlan) {
        if (subscriptionPlan.getName() == null || subscriptionPlan.getName().isEmpty()){
            throw new IllegalArgumentException("Subscription plan name is required");
        }
        if (subscriptionPlan.getPrice() ==  null || subscriptionPlan.getPrice() <= 0){
            throw new IllegalArgumentException("Subscription plan price must be greater than zero");
        }
        return subscriptionPlanRepository.save(subscriptionPlan);
    }

    @Override
    public void deletePlan(Long id) throws SubscriptionPlanNotFoundException {
        SubscriptionPlan subscriptionPlan = getPlanById(id);
        if(!subscriptionPlanRepository.existsById(id)){
            throw new SubscriptionPlanNotFoundException("SubscriptionPlan with id " + id + " not found");
        }
        if (!subscriptionPlan.getUsers().isEmpty()) {
            throw new RuntimeException("Cannot delete subscription plan with assigned users");
        }
        subscriptionPlanRepository.deleteById(id);
    }

    @Override
    public SubscriptionPlan assignPlanToUser(Long userId, Long planId) throws UserNotFoundException, SubscriptionPlanNotFoundException{
        User user = userService.getUserById(userId);
        SubscriptionPlan subscriptionPlan = getPlanById(planId);



        user.setSubscriptionPlan(subscriptionPlan);
        userService.saveUser(user);
        return subscriptionPlan;
    }

}
