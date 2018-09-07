package com.gamecodeschool.instgram;

public class User {

    public String user , email , phone , url;

    public User (){

    }

    public User(String url) {
        this.url = url;
    }

    public User(String user, String email, String phone , String url) {
        this.user = user;
        this.email = email;
        this.phone = phone;
        this.url = url ;
    }

}
