package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            log.info("save meal {} with userId = {}", meal, userId);
            return meal;
        }
        // handle case: update, but not present in storage
        if (userId == repository.get(meal.getId()).getUserId()) {
            log.info("update meal {} with userId = {}", meal, userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        log.info("meal {} does not belong userId = {}", meal, userId);
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Integer mealUserId = repository.get(id).getUserId();
        if (mealUserId == null || mealUserId != userId) {
            log.info("meal id = {} does not belong userId = {} or absent", id, userId);
            return false;
        }
        repository.remove(id);
        log.info("delete meal id={} with userId={}", id, userId);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        Integer mealUserId = repository.get(id).getUserId();
        if (mealUserId == null || mealUserId != userId) {
            log.info("meal id = {} does not belong userId = {} or absent", id, userId);
            return null;
        }
        log.info("get meal id={} with userId={}", id, userId);
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get all meal userId={} from {} to {}", userId, startDate, endDate);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> !meal.getDate().isAfter(endDate) && !meal.getDate().isBefore(startDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> getAll(int userId) {
        return getAll(userId, LocalDate.MIN, LocalDate.MAX);
    }
}

