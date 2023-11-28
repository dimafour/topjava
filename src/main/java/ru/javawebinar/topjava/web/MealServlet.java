package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

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
    private final Storage storage = new MapStorage(MEALS);
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String idString = request.getParameter("id");
        String action = request.getParameter("action");
        List<MealTo> meals = filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("meals", meals);
        if (action == null) {
            request.getRequestDispatcher("meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "edit": {
                int id = Integer.parseInt(idString);
                request.setAttribute("meal", MealsUtil.createTo(storage.get(id), false));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            }
            case "add": {
                request.setAttribute("meal", MealsUtil.createTo(new Meal(), false));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            }
            case "delete": {
                int id = Integer.parseInt(idString);
                storage.delete(id);
                response.sendRedirect("meals");
                break;
            }
            case "clear": {
                storage.clear();
                response.sendRedirect("meals");
                break;
            }
            default:
                throw new IOException("Action " + action + " is not supported");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("id");
        String description = request.getParameter("description");
        String caloriesString = request.getParameter("calories");
        String dateTimeString = request.getParameter("datetime");
        LocalDateTime datetime;
        int calories = caloriesString.isEmpty() ? 0 : Integer.parseInt(caloriesString);
        try {
            datetime = LocalDateTime.parse(dateTimeString);
        } catch (DateTimeParseException e) {
            datetime = LocalDateTime.now().withSecond(0).withNano(0);
        }
        Meal meal;
        if (idString == null) {
            meal = new Meal();
            meal.setDateTime(datetime);
            meal.setCalories(calories);
            meal.setDescription(description);
        } else {
            meal = new Meal(datetime, description, calories);
            meal.setId(Integer.parseInt(idString));
        }
        storage.save(meal.getId(), meal);
        response.sendRedirect("meals");
    }

}
