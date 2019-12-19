package com.example.RestTest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherUser {
    private String id_;
    private String userName_;
    private List<PhoneBookEntry> phoneBooks_;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OtherUser otherUser = (OtherUser) o;
        return Objects.equals(id_, otherUser.id_) &&
                Objects.equals(userName_, otherUser.userName_);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_, userName_);
    }

    public OtherUser(String id, String userName){
        id_ = id;
        userName_ = userName;
        phoneBooks_ = new ArrayList<PhoneBookEntry>();
    }

    public String setUserName(String userName){
        userName_ = userName;
        return userName_;
    }

    public String getId(){
        return id_;
    }

    public String getUserName(){
        return userName_;
    }

    public PhoneBookEntry getPhoneBookEntry(int id){
        return phoneBooks_.get(id);
    }

    public PhoneBookEntry addPhoneBookEntry(PhoneBookEntry phoneBookEntry){
        phoneBooks_.add(phoneBookEntry);
        return phoneBookEntry;
    }

    public PhoneBookEntry setPhoneBookEntry(int entryId, PhoneBookEntry phoneBookEntry){
        phoneBooks_.set(entryId,phoneBookEntry);
        return phoneBookEntry;
    }

    public void deletePhoneBookEntry(int id){
            phoneBooks_.remove(id);
    }

    public List<PhoneBookEntry> getPhoneBooks(){
        return phoneBooks_;
    }

    public boolean findUser(String find){
        boolean isContain = userName_.contains(find);
        return isContain;
    }
}
