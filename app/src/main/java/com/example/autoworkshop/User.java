package com.example.autoworkshop;

public class User {
    public String userName, emailadd, phonenum,password;

    public User(String name, String email, String phone, String pass){
        this.userName=name;
        this.emailadd=email;
        this.phonenum=phone;
        this.password = pass;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailadd() {
        return emailadd;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
