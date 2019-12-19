package com.example.RestTest.dao;

import com.example.RestTest.exception.EntityNotFoundException;
import com.example.RestTest.model.OtherUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OtherUserRepositoryTest {

    @Mock
    private OtherUserRepository otherUserRepository_;

    @BeforeEach
    void setUp() {
        this.otherUserRepository_ = new OtherUserRepositoryImpl();
    }

    @Test
    public void getUserByUserName_Should_Return_True() throws Exception {
        OtherUser alex = otherUserRepository_.getUserByUserName("Alex_Morph");
        assertThat(alex).isNotNull();
        assertThat(alex.getUserName()).isEqualTo("Alex_Morph");
    }

    @Test
    public void getUserByUserName_Null_User() throws Exception {
        OtherUser alex = otherUserRepository_.getUserByUserName("Alex_Morp");
        assertThat(alex).isNull();
    }

    @Test
    public void getUserById_Should_Return_True() throws EntityNotFoundException {
        OtherUser alex = otherUserRepository_.getUserById("1");
        assertThat(alex).isNotNull();
        assertThat(alex.getId()).isEqualTo("1");
    }

    @Test
    public void getUserById_Null_User() throws EntityNotFoundException {
        OtherUser alex = otherUserRepository_.getUserById("10");
        assertThat(alex).isNull();
    }

    @Test
    void findAllUsers_Contain() throws Exception {
        List<OtherUser> allUsers = otherUserRepository_.findAllUsers();
        assertThat(allUsers).contains(new OtherUser("1", "Alex_Morph"));
    }
}