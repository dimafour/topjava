package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @RequestMapping("")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        log.info("redirect to save form");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String getUpdate(Model model, @RequestParam("id") Integer id) {
        log.info("redirect to update form");
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @PostMapping({"/create"})
    public String postCreate(HttpServletRequest request) {
        super.create(parseMeal(request));
        return "redirect:/meals";
    }

    @PostMapping({"/update"})
    public String postUpdate(HttpServletRequest request) {
        super.update(parseMeal(request), Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String filter(Model model, HttpServletRequest request) {
        model.addAttribute("meals", super.getBetween(
                parseLocalDate(request.getParameter("startDate")),
                parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")),
                parseLocalTime(request.getParameter("endTime"))
        ));
        return "meals";
    }

    private Meal parseMeal(HttpServletRequest request) {
        return new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
    }
}
