package com.example.contact.mydatabase;

import org.intellij.lang.annotations.Identifier;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StudentDao {
    //Trả về danh sách của các sinh viên được lưu trong database "student"
    @Query("SELECT * FROM student ORDER BY id ASC")
    List<StudentEntity> getAll();

    //Trả về đội tượng sinh viên có fist_name, last_name tương ứng lần lượt là fistName, lastName;
    @Query("SELECT * FROM student where fist_name LIKE :fistName AND last_name LIKE :lastName")
    StudentEntity findByName(String fistName, String lastName);

    //
    @Query("SELECT * FROM student where id = :id")
    LiveData<StudentEntity> getStudents(int id);

    //Đếm số sinh viên trong database.
    @Query("SELECT COUNT(*) from student")
    int countStudents();


    //Thêm một đối tượng Student vào trong danh sách student;
    @Insert
    void insert(StudentEntity... student);

    @Delete
    void delete(StudentEntity student);

    @Query("DELETE FROM student")
    void deleteAllStudents();

    @Update
    void update(StudentEntity student);

    //Lấy ra địa chị ID của sinh viên cuối cùng.
    @Query("SELECT * FROM student ORDER BY id DESC LIMIT 1")
    StudentEntity getStudentEnd();

    //Update last_name và fist_name thành lastName và fistName của phần tử có địa chỉ id.
    @Query("UPDATE student SET last_name = :lastName, fist_name = :fistName WHERE id = :id")
    void update(int id, String lastName, String fistName);
}