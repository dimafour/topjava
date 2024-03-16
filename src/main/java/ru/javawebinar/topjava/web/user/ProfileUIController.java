package ru.javawebinar.topjava.web.user;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    @GetMapping
    public String profile() {
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            try {
                super.update(userTo, SecurityUtil.authUserId());
            } catch (DataIntegrityViolationException e) {
                addErrorToResult(result, e);
                return "profile";
            }
            SecurityUtil.get().setTo(userTo);
            status.setComplete();
            return "redirect:/meals";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            try {
                super.create(userTo);
            } catch (DataIntegrityViolationException e) {
                addErrorToResult(result, e);
                model.addAttribute("register", true);
                return "profile";
            }
            status.setComplete();
            return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
        }
    }

    private static void addErrorToResult(BindingResult result, DataIntegrityViolationException e) {
        if (ValidationUtil.checkDuplicateParameter(e, "users", "email")) {
            result.addError(new FieldError("userTo", "email", "User with this email already exists"));
        } else {
            result.addError(new ObjectError("userTo", ValidationUtil.getRootCause(e).getMessage()));
        }
    }
}