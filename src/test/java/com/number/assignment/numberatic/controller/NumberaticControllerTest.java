package com.number.assignment.numberatic.controller;


import com.number.assignment.AssignmentApplication;
import com.number.assignment.numberatic.db.model.Numberatic;
import com.number.assignment.numberatic.db.repository.NumberaticRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssignmentApplication.class)
@AutoConfigureMockMvc
public class NumberaticControllerTest {

    private static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private NumberaticRepository numberaticRepository;

    @Before
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        Numberatic numberatic = new Numberatic(0L);
        Numberatic numberatic2 = new Numberatic(10000000L);
        Numberatic numberatic3 = new Numberatic(-1000000L);
        Numberatic numberatic4 = new Numberatic(34L);
        Numberatic numberatic5 = new Numberatic(16L);


        numberaticRepository.save(numberatic);
        numberaticRepository.save(numberatic2);
        numberaticRepository.save(numberatic3);
        numberaticRepository.save(numberatic4);
        numberaticRepository.save(numberatic5);


    }

    @Test
    public void getNumberList() throws Exception {
        mockMvc.perform(get("/assignment")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$", Matchers.hasSize(5)));

    }

    @Test
    public void getNumber() throws Exception {
        mockMvc.perform(get("/assignment/0")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.number", is(0)));

    }

    @Test
    public void getNumberListWithSortAsc() throws Exception {
        mockMvc.perform(get("/assignment/sort/descending")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$[0].number", is(10000000)));

    }

    @Test
    public void getNumberListWithSortDesc() throws Exception {
        mockMvc.perform(get("/assignment/sort/ascending")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$[0].number", is(-1000000)));

    }

    @Test
    public void getMaxNumber() throws Exception {
        mockMvc.perform(get("/assignment/max")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.number", is(10000000)));

    }

    @Test
    public void getMinNumber() throws Exception {
        mockMvc.perform(get("/assignment/min")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.number", is(-1000000)));

    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/assignment/{id}", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotSavedNumber() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/assignment/{id}", "77")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @After
    public void tearDown() {
        // delete all the test data created from the database
        numberaticRepository.deleteByNumber(0L);
        numberaticRepository.deleteByNumber(10000000L);
        numberaticRepository.deleteByNumber(-1000000L);
        numberaticRepository.deleteByNumber(34L);
        numberaticRepository.deleteByNumber(16L);

    }

}
