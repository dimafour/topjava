package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {
    @Test
    void styleCss() throws Exception {
        perform(get("/resources/css/style.css"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/css;charset=UTF-8")));
    }
}
