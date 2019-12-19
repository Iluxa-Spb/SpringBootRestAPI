package com.example.RestTest.model;

public class PhoneBookEntry {
    private String title_;
    private String number_;

    public PhoneBookEntry (String title, String number){
        title_ = title;
        number_ =number;
    }

    public String setTitle(String title){
        title_ = title;
        return title_;
    }

    public String setNumber(String number){
        number_ = number;
        return number_;
    }

    public String getTitle(){
        return title_;
    }
    public String getNumber(){
        return number_;
    }

    public boolean findNumber(String find){
        //boolean isContain = number_.contains(find);
        return number_.contains(find);
    }
}
