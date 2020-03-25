package com.example.contact;

import androidx.annotation.NonNull;

public class Item {
    private int id = 0;
    //private ImageView profilePhoto;
    private String lastName = "";
    private String fistName = "";
    private String number = "";
    private boolean gender = true;
    private String email = "";
    private String createdAt = "";
    private String modifiedAt = "";

    public Item(int id, String lastName, String fistName, String number, String email){
        this.id = id;
        this.lastName = lastName;
        this.fistName = fistName;
        this.number = number;
        this.email = email;
    }

    public Item(int id, String lastName,  String fistName, String number, boolean gender, String email){
        this.id = id;
        this.lastName = lastName;
        this.fistName = fistName;
        this.number = number;
        this.gender = gender;
        this.email = email;
    }

    public Item(int id, String lastName,  String fistName, String number, boolean gender, String email,
                String createdAt, String modifiedAt){
        this.id = id;
        this.lastName = lastName;
        this.fistName = fistName;
        this.number = number;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFistName() {
        return fistName;
    }

    public String getNumber() {
        return number;
    }

    public boolean isGender() {
        return gender;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return ((new Integer(getId())).toString() + " " + getLastName() + " " + getFistName() +
                " " + getNumber() + " " +  (new Boolean(isGender())).toString() + " " + getEmail());
    }
}