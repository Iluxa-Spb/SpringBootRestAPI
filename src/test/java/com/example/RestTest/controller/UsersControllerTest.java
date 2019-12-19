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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersControllerTest {
    private final String URL = "/users/";

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc_;

    @Autowired
    private  UsersController usersController_ ;

    @BeforeAll
    public void setUp() throws Exception{
        this.mockMvc_ = MockMvcBuilders.standaloneSetup(usersController_)
                .build();
        usersController_ = new UsersController();
    }

    @Test
    void testGetList() throws Exception{
        mockMvc_.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value(new String("ID:1 Alex_Morph")));
    }

    @Test
    public void testGetOtherUserById() throws Exception
    {
        mockMvc_.perform( MockMvcRequestBuilders
                .get(URL+"{id}", 3)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").value("3"));
    }

    //@Test
    //public void testAddUser() throws Exception {
    //    System.out.println(TestUtils.objectToJson(new OtherUser("6", "UserName")));
    //    mockMvc_.perform(MockMvcRequestBuilders
    //            .post(URL)
    //            .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
    //            //.content(TestUtils.objectToJson(new OtherUser("6", "UserName")))
    //            .content("{ id: '6', userName: 'Alex_Fiest' }")
    //            .accept(MediaType.APPLICATION_JSON))
    //            .andDo(print())
    //            .andExpect(status().isCreated())
    //            .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    //}

    @Test
    public void testAddUserFromParam() throws Exception {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"create")
                .param("id", "7")
                .param("userName", "Alex"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("7"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Alex"));
    }

    //@Test
    //public void updateUser() throws Exception
    //{
    //    mockMvc_.perform( MockMvcRequestBuilders
    //            .put(URL+"{id}", 2)
    //            //.content(TestUtils.objectToJson(new OtherUser("2", "TestName")))
    //            .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
    //            .content("{ id: '2', userName: 'Alex_Fiest' }"))
    //            //.content("{\"id\":\"6\",\"userName\":\"Vlad\"}"))
    //            //.accept(MediaType.APPLICATION_JSON))
    //            .andDo(print())
    //            .andExpect(status().isOk())
    //            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
    //            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Alex_Fiest"));
    //}

    @Test
    public void testUpdateUserFromParam() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"{id}/update", 2)
                .param("id", "2")
                .param("userName", "Alex"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Alex"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc_.perform( MockMvcRequestBuilders.delete(URL+"{id}", 1) )
                .andExpect(status().isAccepted());
    }

    @Test
    public void testDeleteUser_Exception() throws Exception {
        mockMvc_.perform( MockMvcRequestBuilders.delete(URL+"{id}", 100) )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void TestFindUserFromParam_FIND() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"find_user")
                .param("find", "Al"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
    public void testFindUserFromParam_NOT_FOUND() throws Exception
    {
        mockMvc_.perform(MockMvcRequestBuilders
                .get(URL+"find_user")
                .param("find", "Alice"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}