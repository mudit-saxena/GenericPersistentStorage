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
     final CustomSharedPreference csm = new CustomSharedPreference(getApplicationContext());

        //First set up a Key Value pair
        //Uncomment put methods if going for a first run

        csm.put("KEY","MUDIT");
//          csm.put("INT_VALUE",100);
//          csm.put("LONG_VALUE",987654321021L);
//          csm.put("FLOAT_VAL",0.325f);
//
//
//        // Multilple threads created to check working of lock and safety of db
//
//        ( new Thread(){
//            public void run(){
//                csm.put("T1",1);
//            }
//
//        }).start();
//
//        ( new Thread(){
//            public void run(){
//                csm.put("T2",25L);
//            }
//
//        }).start();
//
//        thread A = new thread(csm);
//
//        ( new Thread(){
//            public void run(){
//                Log.d("t4 3","t4 3 one");
//                csm.put("T4","3");
//            }
//
//        }).start();
//
//        ( new Thread(){
//            public void run(){
//                Log.d("T4 50","T4 50 one");
//                csm.put("T4","50");
//            }
//
//        }).start();
//
//
//
//        //TextView for String Key Value pair
        TextView tv = (TextView) findViewById(R.id.text);
        String val = csm.get("KEY");
        tv.setText(val);
//
//        //TextView for int Key Value pair
//        TextView intTv = (TextView) findViewById(R.id.TextFORINT);
//
//        //TextView for Long Key Value pair
//        TextView longTv = (TextView) findViewById(R.id.TextFORLONG);
//
//        //TextView for Float Key Value pair
//        TextView floatTv = (TextView) findViewById(R.id.TextFORFLOAT);
//
//        // Extract the keyValue pair and set to textView
//        // For checking persistence comment out the putString after first run
//
//        int val = csm.get("INT_VALUE");
//        long lval = csm.get("LONG_VALUE");
//        float fval = csm.get("FLOAT_VAL");
//        String sval = csm.get("T3");
//        tv.setText(sval);
//
//        intTv.setText(Integer.toString(val));
//
//        longTv.setText(Long.toString(lval));
//
//        floatTv.setText(Float.toString(fval));
    }


    // Another thread
//    class thread implements Runnable{
//
//        CustomSharedPreference csm;
//        Thread work;
//        thread(CustomSharedPreference cm){
//            this.csm = cm;
//             work = new Thread(this,"Extern thread");
//            Log.d("Work thread",work.getName());
//            work.start();
//        }
//        public void run(){
//            Log.d("T3 3","T3 90 one");
//            csm.put("T3","90");
//            csm.get("INT_VALUE");
//            csm.get("KEY");
//        }
//    }
}
