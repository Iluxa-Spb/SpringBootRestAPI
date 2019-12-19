package com.example.RestTest.dao;

import com.example.RestTest.exception.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.RestTest.model.OtherUser;

import java.util.List;

@Repository
public interface OtherUserRepository {

    OtherUser getUserByUserName(String userName) throws Exception;
    OtherUser getUserById(String id) throws EntityNotFoundException;

    List<OtherUser> findAllUsers();
}
