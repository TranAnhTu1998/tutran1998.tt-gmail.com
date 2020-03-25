package com.example.contact;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class WriteAndReadFile {

    private AppCompatActivity appCompatActivity;

    private String TAG;
    WriteAndReadFile(AppCompatActivity appCompatActivity, String TAG){
        this.appCompatActivity = appCompatActivity;
        this.TAG = TAG;
    }

    public String ReadFileFromES(String filenameEs) {
        // TODO Auto-generated method stub
        String result = null;
        try {
            // buoc 1 mo file
            FileInputStream fis = new FileInputStream(
                    Environment.getExternalStorageDirectory() + "//"
                            + filenameEs);

// buoc 2 doc file
            int flength = fis.available();

            if (flength != 0) {
                byte[] buffer = new byte[flength];
                if (fis.read(buffer) != -1) {
                    result = new String(buffer);
                }
            }
            // buoc 3 dong file
            fis.close();

        } catch (Exception e) {
            // TODO: handle exception
            //LogUtil.LogE(TAG, e.toString());
        }

        return result;

    }

    public void WriteToFileES(String fname, String fcontent) {
        // TODO Auto-generated method stub

        try {
            // buoc 1 mo file de gi
            FileOutputStream fos = new FileOutputStream(
                    Environment.getExternalStorageDirectory() + "//" + fname);

// buoc 2 ghi noi dung vao file
            fos.write(fcontent.getBytes());

// buoc 3 dong file
            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
            //LogUtil.LogE(TAG, e.toString());
        }

    }

    public void WriteToFile(String filename2, String contentfile2) {
        // TODO Auto-generated method stub
        try {
            // buoc 1 mo file de gi
            FileOutputStream fos = appCompatActivity.openFileOutput(filename2, Context.MODE_PRIVATE);

// buoc 2 ghi noi dung vao file
            fos.write(contentfile2.getBytes());

// buoc 3 dong file
            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
            //LogUtil.LogE(TAG, e.toString());
        }

    }

    public String ReadFromFile(String fName) {
        String result = null;
        try {
            // buoc 1 mo file
            FileInputStream fis = appCompatActivity.openFileInput(fName);

// buoc 2 doc file
            int flength = fis.available();

            if (flength != 0) {
                byte[] buffer = new byte[flength];
                if (fis.read(buffer) != -1) {
                    result = new String(buffer);
                }
            }
            // buoc 3 dong file
            fis.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Lỗi ở hàm Read");
            //LogUtil.LogE(TAG, e.toString());
        }

        return result;
    }

    public String ReadStaticfile(int rsID) {
        String result = null;
        try {
            Resources resources = appCompatActivity.getResources();
            InputStream is = resources.openRawResource(rsID);
            // buoc 2 doc file
            int flength = is.available();

            if (flength != 0) {
                byte[] buffer = new byte[flength];
                if (is.read(buffer) != -1) {
                    result = new String(buffer);
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            //LogUtil.LogE(TAG, e.toString());
        }

        return result;
    }


}
