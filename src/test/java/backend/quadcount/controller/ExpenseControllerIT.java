package backend.quadcount.controller;

import backend.quadcount.model.*;
import backend.quadcount.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Full-stack test: loads Spring context + H2, hits real MVC layer.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")   // <--- Add this line

class ExpenseControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ExpenseRepository expenseRepo;
    @Autowired GroupRepository   groupRepo;
    @Autowired UserRepository    userRepo;
    @Autowired ObjectMapper      json;         // auto-configured by Boot

    Group group;
    User  user;

    @BeforeEach
    void seed() {
        expenseRepo.deleteAll();

        group = groupRepo.save(new Group(null,"Trip",null));
        user  = userRepo.save (new User (null,"alice@test.com","pw","Alice",null,null));

        expenseRepo.save(new Expense(null,"Dinner",20,"","FOOD",false,group,user));
    }

    @Test
    void GET_expenses_returns_wrapper_and_content() throws Exception {
        mvc.perform(get("/api/expenses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.data.content[0].name", is("Dinner")));
    }
}
