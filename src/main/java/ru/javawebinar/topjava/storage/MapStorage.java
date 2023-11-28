package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage {

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MapStorage() {
    }

    public MapStorage(List<Meal> mealList) {
        mealList.forEach(meal -> storage.put(meal.getId(), meal));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(int id, Meal meal) {
        storage.put(id, meal);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void update(Meal meal) {
        storage.put(meal.getId(), meal);
    }
}
