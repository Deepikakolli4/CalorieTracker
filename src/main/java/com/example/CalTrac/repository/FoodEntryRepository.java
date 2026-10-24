
package com.example.CalTrac.repository;

import com.example.CalTrac.model.FoodEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodEntryRepository extends MongoRepository<FoodEntry, String> {

    List<FoodEntry> findByUserId(String userId);

    List<FoodEntry> findByUserIdAndEntryDate(String userId, LocalDate entryDate);

    List<FoodEntry> findByUserIdAndMealType(String userId, String mealType);

    List<FoodEntry> findByUserIdAndEntryDateBetween(String userId, LocalDate startDate, LocalDate endDate);

    List<FoodEntry> findByUserIdAndMealTypeAndEntryDateBetween(
            String userId, String mealType, LocalDate startDate, LocalDate endDate);
}
