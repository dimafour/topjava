package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage mealStorage;

    @Override
    public void init() throws ServletException {
        mealStorage = new MemoryMealStorage();
        testMeals.forEach(mealStorage::save);
        log.debug("saving test data in storage");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            List<MealTo> meals = filteredByStreams(mealStorage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", meals);
            log.debug("redirect to meals - action null - showing meals list");
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "edit": {
                int id = Integer.parseInt(idString);
                request.setAttribute("meal", mealStorage.get(id));
                log.debug("redirect to edit.jsp - action edit");
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            }
            case "add": {
                request.setAttribute("meal", new Meal());
                log.debug("redirect to edit.jsp - action add");
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            }
            case "delete": {
                int id = Integer.parseInt(idString);
                mealStorage.delete(id);
                log.debug("redirect to meals - delete action");
                response.sendRedirect("meals");
                break;
            }
            default: {
                log.debug("redirect to meals - not supported action");
                response.sendRedirect("meals");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, DateTimeParseException, ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("post method");
        String idString = request.getParameter("id");
        LocalDateTime datetime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(datetime, description, calories);
        meal.setId(idString.isEmpty() ? null : Integer.parseInt(idString));
        mealStorage.save(meal);
        log.debug("redirect to meals - saving meal in storage");
        response.sendRedirect("meals");
    }
}
