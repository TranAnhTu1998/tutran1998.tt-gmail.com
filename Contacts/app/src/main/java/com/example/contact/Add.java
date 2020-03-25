package com.example.contact;

import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.contact.mydatabase.StudentDao;
import com.example.contact.mydatabase.StudentDatabase;
import com.example.contact.mydatabase.StudentEntity;
import com.example.contact.mydatabase.StudentRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class Add extends AppCompatActivity {

    //
    //Tọa đối tượng strContact để chứa chuỗi trong file JSON.
    private String strContact;
    //Tạo ArrayList<com.example.contacts.MainActivity.Item> để chứa danh sách các Itrem trong danh bạ
    //private ArrayList<Item> arrayListContacts;
    //
    private ListItemAdapter listItemAdapter;
    //
    public  String path = "contacts.json";
    //
    private String TAG = Add.class.getSimpleName();
    private EditText edtName;
    private EditText edtNumber;
    private EditText edtEmail;
    private RadioGroup rdogrGender;
    private RadioButton rbtMale;
    private RadioButton rbtFemale;
    private Button btnAdd;
    private ListView lvContants;
    private static ArrayList<Item> arlContact;
    private static StudentRepository stdRepository;
    //


    public  String getStrContact() {
        return strContact;
    }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Khởi tạo cho các đối tượng.
        edtName = findViewById(R.id.edt_name);
        edtNumber = findViewById(R.id.edt_number);
        edtEmail = findViewById(R.id.edt_email);
        rbtMale = findViewById(R.id.rbt_male);
        rbtFemale = findViewById(R.id.rbt_female);
        lvContants = findViewById(R.id.lv_contacts);
        btnAdd = findViewById(R.id.btn_add_contact);
        //arrayListContacts = new ArrayList<Item>();
        arlContact = new ArrayList<Item>();
        stdRepository = new StudentRepository(getApplicationContext());
        MainActivity.studentRepository = stdRepository;

        //
        //Khởi tạo đối tượng gán và con trỏ listView
        //lvContants = findViewById(R.id.lv_contacts_main);

        //Khởi tạo đối tượng.


        //Sửa lại file Json thành Contacts và xóa toàn bộ nội dung.

        //Hiện danh bạ cũ liên màn hình người dùng.
        restartListViewDataBase();
        //

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id;
                if(arlContact != null){
                    id = arlContact.size()+1;
                }
                else {
                    id = 1;
                }

                String strName = edtName.getText().toString();
                String strNumber = edtNumber.getText().toString();
                String strEmail = edtEmail.getText().toString();
                boolean  blGender = rbtMale.isChecked();

                //Nạp vào một Item
                Item item = new Item(id,"",strName, strNumber, blGender, strEmail,getCurrentDateTime(),getCurrentDateTime());

                System.out.println("112item" + item.toString());

                //Bắt đầu quá trình đưa dữ liệu lên Database
                stdRepository.insertStudent(item);
                //

                /*//Nạp vaò aarrayListContacts  thông qua arlContact;
                arlContact.add(item);

                /*Ghi dữ liệu ra file json constans.json*/
                //Tạo một dối tượng StringWriter;
                /*StringWriter stringWriter = new StringWriter();

                //Tạo một đối tượng JsonWriter;
                JsonWriter jsonWriter = new JsonWriter(stringWriter);
                //Bắt đầu tạo // {"id":4,"lastName":"","fistName":"Торхоов А.Е.","number": "Не знаю", "email": "atorkhov@gmail.com"}
                try {
                    jsonWriter.beginObject();
                    jsonWriter.name("id").value(item.getId());
                    jsonWriter.name("lastName").value(item.getLastName());
                    jsonWriter.name("fistName").value(item.getFistName());
                    jsonWriter.name("number").value(item.getNumber());
                    jsonWriter.name("gender").value(item.isGender());
                    jsonWriter.name("email").value(item.getEmail());
                    jsonWriter.endObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Quy trình nạp dữ liệu stringWrier vào vị trí thích hợp trong file JSON
                //Thông tin về Item mới
                String strItem = stringWriter.toString();
                if(strContact != null){
                    //Chèn vào danh sách những tê người dùng trước đó.
                    StringBuffer strbfContact = new StringBuffer(strContact);
                    strbfContact.insert(strContact.length()-1,",\n" + strItem);
                    //Dữ liệu danh bạ đã được cập nhập lại(strContact update)
                    strContact = strbfContact.toString();
                }
                else {
                    strContact = "[" + strItem + "]";
                }
                //Cập nhật lại strContact;
                //MainActivity.setStrContact(strContact);
                //System.out.println("strcontact : " + strContact);
                //Nạp vào file json contact.jon.
                WriteAndReadFile writeAndReadFile = new WriteAndReadFile(Add.this, TAG);
                writeAndReadFile.WriteToFile(path,strContact);*/
                //Cập nhật lại danh bạ hiện thị người dùng
                //listItemAdapter.notifyDataSetChanged();
                arlContact = new ArrayList<Item>();
                restartListViewDataBase();
                //Kiem tra du lieu
                //System.out.println("ReadFromFile(path) : " + writeAndReadFile.ReadFromFile(path));
                //display file saved message
                Toast.makeText(getBaseContext(), "File saved successfully!",
                        Toast.LENGTH_SHORT).show();




            }
        });
        //restartListViewDataBase();
    }

    private void restartListViewFile() {
        //Lấy được chuỗi trong file JSON và nạp vào strContact.
        strContact = convertFileJsonToString(Add.this);
        System.out.println("strContact : " + strContact);
        //MainActivity.setStrContact(strContact);
        //Kiểm tra xem JSON có dữ liệu hay không?
        if(strContact != null){
            //Nạp file json lên ArrayList.
            arlContact = convertStringToArrayList(strContact);
            System.out.println("arlContact" + arlContact);
            //MainActivity.setArrayListContacts(arlContact);
            //Dưa dữ liệu lên Adapter
            listItemAdapter = new ListItemAdapter(Add.this,R.layout.form_item, arlContact);

            //Dưa dữ liệu lên màn hình người dùng.
            lvContants.setAdapter(listItemAdapter);
        }
        else{
            Log.e(TAG, "Could not get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "No contacts", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void restartListViewDataBase(){
        //Lấy dữ liệu từ trên Databasa xuống và đổ vào ArrayList
        stdRepository.getAllItemStudent(arlContact);
        //System.out.println("arlContact : " + arlContact);
        //MainActivity.setArrayListContacts(arlContact);
        if(arlContact != null){
            //System.out.println("111arlContact_TruocKhiDuaListAdapter : " + arlContact);
            listItemAdapter = new ListItemAdapter(Add.this,R.layout.form_item, arlContact);
            //System.out.println("111arlContact_SauKhiListAdapterDuocgoi : " + arlContact);
            //Dưa dữ liệu lên màn hình người dùng.
            lvContants.setAdapter(listItemAdapter);
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
    }



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
                //Nạp dự liệu vào một đối tượng Item;
                Item itemContact = new Item(id,lastName,fistName,number,blGender,email);
                //
                //System.out.println(itemContact.toString());
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
        WriteAndReadFile writeAndReadFile = new WriteAndReadFile(appCompatActivity, TAG);
        string = writeAndReadFile.ReadFromFile(path);
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

    public String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date); //2016/11/16 12:08:43
    }
}
