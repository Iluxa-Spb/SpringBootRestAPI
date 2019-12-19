package com.example.RestTest.dao;

import com.example.RestTest.exception.EntityNotFoundException;
import com.example.RestTest.model.OtherUser;
import com.example.RestTest.model.PhoneBookEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtherUserRepositoryImpl implements OtherUserRepository {
    private List<OtherUser> users_;

    public OtherUserRepositoryImpl() {
        this.users_ =new ArrayList<OtherUser>();

        users_.add(new OtherUser("1","Alex_Morph"));
        users_.add(new OtherUser("2","Bob_Marth"));
        users_.add(new OtherUser("3","Henry_Aliert"));
        users_.add(new OtherUser("4","Max_Pierce"));
        users_.add(new OtherUser("5","Katrin_Mell"));

        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Alex", "89118987654"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Katy", "89117876578"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Mark", "89119118789"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Marry", "89116785676"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Cooper", "89118908765"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Bell", "89111113245"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Clara", "89112223245"));
        users_.get(0).addPhoneBookEntry(new PhoneBookEntry("Ben", "89113234356"));
    }

    @Override
    public OtherUser getUserByUserName(String userName) throws Exception {
        return users_.stream().filter(user -> user.getUserName().equals(userName)).findAny().orElse(null);
    }

    @Override
    public OtherUser getUserById(String id) throws EntityNotFoundException {
        return users_.stream().filter(user -> user.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public List<OtherUser> findAllUsers() {
        return users_;
    }
}
