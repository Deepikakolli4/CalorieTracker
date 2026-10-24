
package com.example.CalTrac.service;

import com.example.CalTrac.model.FoodEntry;
import com.example.CalTrac.repository.FoodEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FoodEntryService {

    private final FoodEntryRepository foodEntryRepository;

    public FoodEntryService(FoodEntryRepository foodEntryRepository) {
        this.foodEntryRepository = foodEntryRepository;
    }

    // Create a food entry
    public FoodEntry createFoodEntry(FoodEntry foodEntry) {
        foodEntry.setId(null);
        return foodEntryRepository.save(foodEntry);
    }

    // Get all food entries for a user
    public List<FoodEntry> getFoodEntries(String userId) {
        return foodEntryRepository.findByUserId(userId);
    }

    // Get food entries for a particular date
    public List<FoodEntry> getFoodEntriesByDate(String userId, LocalDate date) {
        return foodEntryRepository.findByUserIdAndEntryDate(userId, date);
    }

    // Get food entries for a meal type
    public List<FoodEntry> getFoodEntriesByMealType(String userId, String mealType) {
        return foodEntryRepository.findByUserIdAndMealType(userId, mealType);
    }

    // Get food entries between two dates
    public List<FoodEntry> getFoodEntriesByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date");
        }
        return foodEntryRepository.findByUserIdAndEntryDateBetween(
                userId, startDate, endDate);
    }

    // Get food entries by meal type and date range
    public List<FoodEntry> getFoodEntriesByMealAndDateRange(String userId, String mealType,
                                                            LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Start date cannot be after end date");
        }

        return foodEntryRepository
                .findByUserIdAndMealTypeAndEntryDateBetween(
                        userId, mealType, startDate, endDate);
    }

    // Update an existing food entry
    public FoodEntry updateFoodEntry(String id, String userId, FoodEntry updatedEntry) {
        FoodEntry existingEntry = foodEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Food entry not found"));

        if (!existingEntry.getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                    "You cannot update another user's food entry");
        }
        updatedEntry.setId(id);
        updatedEntry.setUserId(userId);
        return foodEntryRepository.save(updatedEntry);
    }

    // Delete a food entry
    public void deleteFoodEntry(String id, String userId) {
        FoodEntry existingEntry = foodEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Food entry not found"));
        if (!existingEntry.getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                    "You cannot delete another user's food entry");
        }
        foodEntryRepository.deleteById(id);
    }
}
