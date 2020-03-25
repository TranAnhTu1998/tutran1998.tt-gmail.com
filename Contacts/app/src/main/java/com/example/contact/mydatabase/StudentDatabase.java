package com.example.contact.mydatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StudentEntity.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {

    //Khởi tạo một đốt tượng UserStudent;
    public abstract StudentDao daoStudent();
}