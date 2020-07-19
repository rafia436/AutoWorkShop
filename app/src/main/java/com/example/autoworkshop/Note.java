package com.example.autoworkshop;

public class Note {
    String Name,Phone,ID;

    public Note() {
    }
    public Note(String Name, String Phone, String ID){
        this.Name = Name;
        this.Phone = Phone;
        this.ID = ID;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String username) {
        this.Name = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phoneNumber) {
        this.Phone = phoneNumber;
    }


}
