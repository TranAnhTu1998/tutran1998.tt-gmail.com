package com.example.contact.mydatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.contact.Item;

import java.util.Date;

//Entity
//Định nghĩa database Student
@Entity(tableName = "student")
public class StudentEntity {

    //Mỗi một hàng trong bảng "Student" bao gồm
    //Địa chỉ IP, Được đánh tự động.
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Cột last_name
    @ColumnInfo(name = "last_name")
    private String lastName = "";

    //Cột fistName
    @ColumnInfo(name = "fist_name")
    private String fistName = "";

    @ColumnInfo(name = "number")
    private String number = "";

    @ColumnInfo(name = "gender")
    private boolean gender = true;

    @ColumnInfo(name = "email")
    private String email = "";

    @ColumnInfo(name = "created_at")
    private String createdAt = "";
    //@TypeConverters({TimestampConverter.class})

    @ColumnInfo(name = "modified_at")
    private String modifiedAt = "";
    //@TypeConverters({TimestampConverter.class})

    public void setId(int uid) {
        this.id = uid;
    }

    public int getId(){
            return id;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getFistName() {
        return fistName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setGender(boolean gender1) {
        //System.out.println("gender : " + gender1);
        if(gender1){
            this.gender = true;
        }
        else {
            this.gender = false;
        }
    }



    public boolean isGender() {
        return gender;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

}

