package com.example.contact.mydatabase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.contact.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

//Class StudentRepository cung cấp các phương thức giao tiếp với người dùng.
public class StudentRepository {
    //Đặt tên cho database
    private String DB_NAME = "db_student";

    //Tạo một đối tượng StudentDatabase, tên là studentDatabase;
    private StudentDatabase studentDatabase;

    /**/
    //Contractors
    public StudentRepository(Context context){
        studentDatabase = Room.databaseBuilder(context,StudentDatabase.class,DB_NAME).fallbackToDestructiveMigration().build();
    }

    /*Mẫu insert dùng chung*/
    public void insertStudent(final StudentEntity studentEntity){
        //studentEntity.setCreatedAt(getCurrentDateTime());
        //studentEntity.setModifiedAt(getCurrentDateTime());
        new AsyncTask<Void, Void, Void >(){
          @Override
          protected Void doInBackground(Void ... Void){
              studentDatabase.daoStudent().insert(studentEntity);
              return null;
          }
        }.execute();
    }
    /**************************************************************/


    //Chèn một phần tử sinh viên vào trong database.
    public void insertStudent(Item item){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setLastName(item.getLastName());
        studentEntity.setFistName(item.getFistName());
        studentEntity.setNumber(item.getNumber());
        studentEntity.setGender(item.isGender());
        //System.out.println("item.isGender(): " + item.isGender());
        studentEntity.setEmail(item.getEmail());
        studentEntity.setCreatedAt(getCurrentDateTime());
        studentEntity.setModifiedAt(getCurrentDateTime());
        insertStudent(studentEntity);
    }


    /*Phương thức tổng quát chung để xóa */
    public void deleteStudent(final StudentEntity studentEntity){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                studentDatabase.daoStudent().delete(studentEntity);
                return null;
            }
        }.execute();
    }
    /*******************************************************************/


    public LiveData<StudentEntity> getStudents(int id){
        return studentDatabase.daoStudent().getStudents(id);

    }
    /*Phương thức tổng quát chung để xóa */
    //Xóa một phần tử trong database dựa vào địa chỉa Id;
    public void deleteStudent(final int id){
        final LiveData<StudentEntity> studentEntityList = getStudents(id);
        if(studentEntityList != null){
            new AsyncTask<Void, Void, Void>(){

                @Override
                protected Void doInBackground(Void... voids) {
                    studentDatabase.daoStudent().delete(studentEntityList.getValue());
                    return null;
                }
            }.execute();
        }
    }
    /*******************************************************************/


    //Xóa một phần tử trong database dữ vào lastName và fistName;
    public void deleteStudent(final String fistName,final String lastName){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                StudentEntity studentEntity = studentDatabase.daoStudent().findByName(fistName,lastName);
                deleteStudent(studentEntity);
                return null;
            }
        }.execute();
    }
    /*****************************************************************/


    public void deleteAllStudents(final String fistName,final String lastName){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                studentDatabase.daoStudent().deleteAllStudents();
                return null;
            }
        }.execute();
    }
    /*****************************************************************/

    //Update một phần từ vào trong database.
    public void update(final StudentEntity studentEntity) {
        studentEntity.setCreatedAt(getCurrentDateTime());
        studentEntity.setModifiedAt(getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                studentDatabase.daoStudent().update(studentEntity);
                return null;
            }
        }.execute();
    }




    public void my_update() {
        final String LAST_NAME = "Иванов";
        final String FIST_NAME = "Иван Иванович";
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                StudentEntity m_studentEntity = new StudentEntity();
                m_studentEntity = studentDatabase.daoStudent().getStudentEnd();
                m_studentEntity.setLastName(LAST_NAME);
                m_studentEntity.setFistName(FIST_NAME);
                m_studentEntity.setModifiedAt(getCurrentDateTime());
                studentDatabase.daoStudent().update(m_studentEntity);
                return null;
            }
        }.execute();
    }

    public void getAllItemStudent(final ArrayList<Item> listItem){
        //final ;
        //final ArrayList<Item> listItem = new ArrayList<Item>();
        /*studentDatabase.daoStudent().getAll().observe(contaxt, new Observer<List<StudentEntity>>() {
            @Override
            public void onChanged(List<StudentEntity> listStudent) {
                for(StudentEntity note : listStudent) {
                    int id = note.getId();
                    String lastName = note.getLastName();
                    String fistName = note.getFistName();
                    String number = note.getNumber();
                    String email = note.getEmail();
                    Item item = new Item(id, lastName, fistName, number, email);
                    listItem.add(item);
                    //System.out.println("listItem_TrongHam:: " + listItem);
                }
            }
        });*/
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<StudentEntity> listStudent = studentDatabase.daoStudent().getAll();
                //System.out.println("listStudent:: " + listStudent);
                for(StudentEntity note : listStudent){
                    int id = note.getId();
                    String lastName = note.getLastName();
                    String fistName = note.getFistName();
                    String number = note.getNumber();
                    Boolean gender = note.isGender();
                    String email = note.getEmail();
                    String created_at = note.getCreatedAt();
                    String modified_at = note.getModifiedAt();
                    Item item = new Item(id,lastName,fistName,number,gender,email,created_at,modified_at);
                    listItem.add(item);
                }
                return null;
            }
        }.execute();
        //System.out.println("listItem_TruocReturn " + listItem);
        //return listItem;
    }

    public String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date); //2016/11/16 12:08:43
    }
}
