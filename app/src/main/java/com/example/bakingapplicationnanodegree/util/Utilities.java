package com.example.bakingapplicationnanodegree.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utilities {

    public static String readJsonFile(String filename) {
        String json=null;
        try{
            InputStream is = MyApplication.getAppContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer= new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


}
