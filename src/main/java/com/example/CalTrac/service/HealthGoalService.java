
package com.example.CalTrac.service;

import com.example.CalTrac.model.HealthGoal;
import com.example.CalTrac.repository.HealthGoalRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HealthGoalService {

    private final HealthGoalRepository healthGoalRepository;
    public HealthGoalService(HealthGoalRepository healthGoalRepository) {
        this.healthGoalRepository = healthGoalRepository;
    }

    //Goal Creation
    public HealthGoal createGoal(HealthGoal goal) {
        validateGoal(goal);
        if (goal.getUserId() == null || goal.getUserId().isBlank()) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (healthGoalRepository.findByUserId(goal.getUserId()).isPresent()) {
            throw new IllegalArgumentException("A health goal already exists for this user");
        }

        goal.setId(null);
        goal.setCreatedAt(Instant.now());
        goal.setUpdatedAt(Instant.now());
        return healthGoalRepository.save(goal);
    }

    //Get Goal By ID
    public HealthGoal getGoalByUserId(String userId) {
        return healthGoalRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Health goal not found"));
    }

    //Update Goal

    public HealthGoal updateGoal(String userId, HealthGoal updatedGoal) {
        validateGoal(updatedGoal);

        HealthGoal existingGoal = getGoalByUserId(userId);
        existingGoal.setDailyCalorieTarget(updatedGoal.getDailyCalorieTarget());
        existingGoal.setProteinTarget(updatedGoal.getProteinTarget());
        existingGoal.setCarbsTarget(updatedGoal.getCarbsTarget());
        existingGoal.setFatTarget(updatedGoal.getFatTarget());
        existingGoal.setCurrentWeightKg(updatedGoal.getCurrentWeightKg());
        existingGoal.setTargetWeightKg(updatedGoal.getTargetWeightKg());
        existingGoal.setUpdatedAt(Instant.now());

        return healthGoalRepository.save(existingGoal);
    }

    //Goal Validation
    private void validateGoal(HealthGoal goal) {

        if (goal == null) {
            throw new IllegalArgumentException("Goal is required");
        }

        if (goal.getDailyCalorieTarget() <= 0) {
            throw new IllegalArgumentException(
                    "Daily calorie target must be greater than zero");
        }

        if (goal.getProteinTarget() < 0
                || goal.getCarbsTarget() < 0
                || goal.getFatTarget() < 0) {
            throw new IllegalArgumentException(
                    "Macronutrient targets cannot be negative");
        }

        if (goal.getCurrentWeightKg() < 0
                || goal.getTargetWeightKg() < 0) {
            throw new IllegalArgumentException(
                    "Weight cannot be negative");
        }
    }
}
