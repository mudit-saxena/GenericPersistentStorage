package com.example.muditboss.keyvalue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.muditboss.keyvalue.data.CustomSharedPreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating the CustomSharedPreference
        CustomSharedPreference csm = new CustomSharedPreference(getApplicationContext());

        //First set up a Key Value pair
        //Uncomment put methods if going for a first run

         // csm.putString("KEY","MUDIT");
         // csm.putInt("INT_VALUE",100);
         // csm.putLong("LONG_VALUE",987654321021L);
         // csm.putFloat("FLOAT_VAL",0.325f);

        //TextView for String Key VAlue pair
        TextView tv = (TextView) findViewById(R.id.text);

        //TextView for int Key Value pair
        TextView intTv = (TextView) findViewById(R.id.TextFORINT);

        //TextView for Long Key Value pair
        TextView longTv = (TextView) findViewById(R.id.TextFORLONG);

        //TextView for Float Key Value pair
        TextView floatTv = (TextView) findViewById(R.id.TextFORFLOAT);

        // Extract the keyValue pair and set to textView
        // For checking persistence comment out the putString after first run

        int val = csm.getInt("INT_VALUE");
        long lval = csm.getLong("LONG_VALUE");
        float fval = csm.getFloat("FLOAT_VAL");

        tv.setText(csm.getString("KEY"));

        intTv.setText(Integer.toString(val));

        longTv.setText(Long.toString(lval));

        floatTv.setText(Float.toString(fval));
    }
}
