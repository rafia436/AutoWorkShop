package com.example.autoworkshop;

public class Admin {
public String userName, emailadd, phonenum,password;

    public Admin(String name, String email, String phone, String pass){
        this.userName=name;
        this.emailadd=email;
        this.phonenum=phone;
        this.password = pass;
    }

    public Admin() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getemailadd() {
        return emailadd;
    }

    public void setemailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    public String getphonenum() {
        return phonenum;
    }

    public void setphonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
