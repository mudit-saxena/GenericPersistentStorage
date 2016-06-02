package com.example.muditboss.keyvalue.data;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 */
public class SerializeObject {

    public static <T> byte[] getObjectToByte(T value){
        byte[] byteArrayObject = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);

            oos.close();
            bos.close();
            byteArrayObject = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return byteArrayObject;
        }
        return byteArrayObject;
    }

    public static Object getByteToObject(byte[] convertObject){
        Object obj = null;

        ByteArrayInputStream bais;
        ObjectInputStream ins;
        try {

            bais = new ByteArrayInputStream(convertObject);

            ins = new ObjectInputStream(bais);
            obj = ins.readObject();

            ins.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Object type",obj.getClass().getName());
        return obj;
    }
}
