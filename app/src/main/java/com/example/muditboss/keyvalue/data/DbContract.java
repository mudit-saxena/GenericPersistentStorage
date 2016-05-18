package com.example.muditboss.keyvalue.data;

import android.provider.BaseColumns;

/**
 *
 */
public class DbContract {

    public static class KeyValue implements BaseColumns{

        //Table Name
        public static final String TABLE_NAME = "keyStore";

        //Column for key
        public static final String COLUMN_KEY = "key";

        //Column for value
        public static final String COLUMN_VALUE = "value";

    }
}
