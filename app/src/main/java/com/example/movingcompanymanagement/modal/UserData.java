package com.example.movingcompanymanagement.modal;

import java.io.Serializable;

public class UserData implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String role;
    private String id;

    public UserData(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }

    public String getId(){return this.id;}

    public void setId(String id){this.id = id;}
}
