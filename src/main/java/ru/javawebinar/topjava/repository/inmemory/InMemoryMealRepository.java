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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            log.info("save meal {} with userId = {}", meal, userId);
            return meal;
        }
        Meal mealFromRep = repository.get(meal.getId());
        if (mealFromRep == null || userId != mealFromRep.getUserId()) {
            log.info("meal {} does not belong userId = {} or absent", meal, userId);
            return null;
        }
        log.info("update meal {} with userId = {}", meal, userId);
        meal.setUserId(userId);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.info("meal id = {} does not belong userId = {} or absent", id, userId);
            return false;
        }
        log.info("delete meal id={} with userId={}", id, userId);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != userId) {
            log.info("meal id = {} does not belong userId = {} or absent", id, userId);
            return null;
        }
        log.info("get meal id={} with userId={}", id, userId);
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get all meal userId={} from {} to {}", userId, startDate, endDate);
        return filterByPredicate(userId, meal -> !meal.getDate().isAfter(endDate) && !meal.getDate().isBefore(startDate));
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all meal userId={}", userId);
        return filterByPredicate(userId, meal -> true);
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

