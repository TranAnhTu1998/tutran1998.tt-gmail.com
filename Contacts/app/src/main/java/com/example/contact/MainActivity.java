package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


//import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.example.contact.mydatabase.StudentRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //User
    //TAG
    private String TAG = MainActivity.class.getSimpleName();

    //Khai báo DataBase
    //DatabaseMetaData databaseMetaData;


    //Tọa đối tượng strContact để chứa chuỗi trong file JSON.
    private String strContact;

    //Tạo ArrayList<com.example.contacts.MainActivity.Item> để chứa danh sách các Itrem trong danh bạ
    public   ArrayList<Item> arrayListContacts;

    //Adapter
    private ListItemAdapter listItemAdapter;

    //ListView chính
    private ListView listView;

    public static StudentRepository studentRepository = null;

    //Button Display show
    private FloatingActionButton fabtnShow;

    //Button Add
    private FloatingActionButton fabtnAdd;

    //
    private  FloatingActionButton fabtnUpdate;

    //
    public static String path = "contacts.json";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo đối tượng gán và con trỏ listView
        listView = findViewById(R.id.lv_contacts_main);

        //Khởi tạo đối tượng.
        //studentRepository = new StudentRepository(getApplicationContext());


        //Khoi tạo đối tượng arrayList
        arrayListContacts = new ArrayList<Item>();

        /*Button Show*/
        fabtnShow = findViewById(R.id.fabtn_show);
        fabtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
            }
        });

        /*Button Add*/
        //Sửa lại file Json thành Contacts và xóa toàn bộ nội dung.
        fabtnAdd = findViewById(R.id.fabtn_add);
        //upDateListView();
        fabtnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent itAdd = new Intent(MainActivity.this, Add.class);
                //Intent itAdd = new Intent(MainActivity.this, Add.class);
                startActivity(itAdd);
            }
        });

        /**/
        fabtnUpdate = findViewById(R.id.fabtn_uppdate);
        fabtnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                studentRepository.my_update();
                restart();
            }
        });

    }

    private void restart() {
        if(arrayListContacts != null && studentRepository != null){
            arrayListContacts = new ArrayList<Item>();
            studentRepository.getAllItemStudent(arrayListContacts);
            /*********************/
            /*Thread t = new Thread(){
                @Override
                public void run(){
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();*/
            /*************************************************/

            //System.out.println("111arlContact_TruocKhiDuaListAdapter : " + arlContact);
            listItemAdapter = new ListItemAdapter(MainActivity.this,R.layout.form_item, arrayListContacts);
            //System.out.println("111arlContact_SauKhiListAdapterDuocgoi : " + arlContact);
            //Dưa dữ liệu lên màn hình người dùng.
            listView.setAdapter(listItemAdapter);
            //System.out.println("111arlContact_SauKhisetAdapter : " + arlContact);
        }
        else
        {
            Log.e(TAG, "No data.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
                }
            });
        }
        //Lấy được chuỗi trong file JSON và nạp vào strContact.
        //strContact = convertFileJsonToString(MainActivity.this);

        /*System.out.println("arrayListContacts " + arrayListContacts);
        //Kiểm tra xem JSON có dữ liệu hay không?
        if(arrayListContacts != null){
            //Nạp file json lên ArrayList.
            //arrayListContacts = convertStringToArrayList(strContact);

            //Dưa dữ liệu lên Adapter
            listItemAdapter = new ListItemAdapter(MainActivity.this,R.layout.form_item, arrayListContacts);

            //Dưa dữ liệu lên màn hình người dùng.
            listView.setAdapter(listItemAdapter);
        }
        else{
            Log.e(TAG, "Could not get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "No contacts", Toast.LENGTH_LONG).show();
                }
            });
        }*/
    }

    /*private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Toast.makeText(ViewPager_Activity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {

            strContact = convertFileJsonToString(MainActivity.this);


            if(strContact != null){

            }else{
                Log.e(TAG, "Could not get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Could not get json from server. Checlk LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            listItemAdapter = new ListItemAdapter(MainActivity.this,R.layout.form_item, arrayListContacts);
            listView.setAdapter(listItemAdapter);
        }
    }*/


    private ArrayList<Item> convertStringToArrayList(String strJson) {
        ArrayList<Item> arrayListItem = new ArrayList<Item>();
        try {
            //Convert String sang JSONArray
            JSONArray arrayJson = new JSONArray(strJson);

            //JSONArray contacts = jsonObject.getJSONArray("contacts");//???
            for(int i = 0; i < arrayJson.length(); i++){
                //
                JSONObject jsonObject = arrayJson.getJSONObject(i);
                //
                int id = jsonObject.getInt("id");
                String lastName = jsonObject.getString("lastName");
                String fistName = jsonObject.getString("fistName");
                String number = jsonObject.getString("number");
                Boolean blGender = jsonObject.getBoolean("gender");
                String email = jsonObject.getString("email");
                //
                Item itemContact = new Item(id,lastName,fistName,number,blGender,email);
                //
                arrayListItem.add(itemContact);
            }
        }catch(final JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return arrayListItem;
    }

    private String convertFileJsonToString(AppCompatActivity appCompatActivity) {
        String string = null;
        //WriteAndReadFile writeAndReadFile = new WriteAndReadFile(, TAG);

        //string = writeAndReadFile.ReadFromFile(path);

        string = strContact;
        /*try {
            //Nạp file lên InputStream
            InputStream  inputStream = (appCompatActivity).getResources().openRawResource(R.raw.contacts_10);

            //Cần Convert stream sang String
            string = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return string;
    }

    private String convertStreamToString(InputStream inputStream) {
        //Tạo mội đối tượng InputStreamReader
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


        //Cần tạo bufferedReader từ inputStream
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //Tạo stringLine để chứa các dòng trong file JSON
        String strLine;

        //Tạp đối tượng StringBuilder để chứa dữ liệu từ file JSON
        StringBuilder stringBuilder = new StringBuilder();

        //Nạp dữ liệu và stringBuilder
        try {
            while ((strLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(strLine).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


}
