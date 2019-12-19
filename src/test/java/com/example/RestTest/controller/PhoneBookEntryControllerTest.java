package com.example.RestTest.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PhoneBookEntryControllerTest {
        private final String URL = "/users/1/";

        @Autowired
        private MockMvc mockMvc_;

        @Autowired
        private  UsersController usersController_ ;

        @BeforeAll
        public void setUp() throws Exception {
            this.mockMvc_ = MockMvcBuilders.standaloneSetup(usersController_)
                    .build();
            usersController_ = new UsersController();
        }

    @Test
    void testGetPhoneBook() throws Exception{
        mockMvc_.perform(get(URL+"phonebook")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(new String("Alex")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].number").value(new String("89118987654")));
    }

    @Test
    public void testGetEntry() throws Exception
    {
        mockMvc_.perform( MockMvcRequestBuilders
                .get(URL+"phonebook/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Katy"));
    }

    @Test
    public void testAddUserFromParam() throws Exception {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"create")
                .param("title", "Marry")
                .param("number", "88889998789"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Marry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("88889998789"));
    }

    @Test
    public void testUpdateUserFromParam() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"phonebook/{id}/update", 1)
                .param("title", "Brad")
                .param("number", "00998877"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Brad"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("00998877"));
    }

    @Test
    public void testDeleteEntry() throws Exception {
        mockMvc_.perform( MockMvcRequestBuilders.delete(URL+"phonebook/{id}", 3) )
                .andExpect(status().isAccepted());
    }

    @Test
    public void testFindEntryFromParam_FIND() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"find_entry")
                .param("find", "7654"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    public void testFindEntryFromParam_NOT_FOUND() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"find_entry")
                .param("find", "Alice"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
