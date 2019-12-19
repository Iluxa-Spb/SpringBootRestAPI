package com.example.RestTest.services;

import com.example.RestTest.dao.OtherUserRepository;
import com.example.RestTest.model.OtherUser;

public class OtherUserService {
    private OtherUserRepository repository;


    public OtherUserService(OtherUserRepository repository) {
        this.repository = repository;
    }

    public boolean checkUserPresence(OtherUser otherUser) throws Exception{
        OtherUser user = repository.getUserByUserName(otherUser.getUserName());
        return user != null;
    }
}
