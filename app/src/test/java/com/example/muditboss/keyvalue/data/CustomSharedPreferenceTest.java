package com.example.muditboss.keyvalue.data;


import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

//import android.support.test.runner.AndroidJUnit4;
//import static android.support.test.InstrumentationRegistry.getTargetContext;


//@RunWith(AndroidJUnit4.class)
public class CustomSharedPreferenceTest extends AndroidTestCase{


//
    @Mock
    Context mContext ;



    CustomSharedPreference csm;
    DbHelper dbHelper;

    @Before
    public void setUp() throws Exception {

         super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(),"_test");
        assertThat(context).isNotNull();
        dbHelper = new DbHelper(context);
        csm = new CustomSharedPreference(context);

    }


    @Test
    public void testPutInt() throws Exception {

          csm.putInt("Key", 4);
          assertThat(csm.getInt("Key")).isEqualTo(4)   ;

    }

    @Test
    public void testGetInt() throws Exception {

        csm.getInt("Key");
       // verify(csm).getInt("Key");
    }

    @Test
    public void testGetLong() throws Exception {

    }

    @Test
    public void testPutLong() throws Exception {
        csm.putLong("Key", 100L);

        //verify(csm).putLong("Key",100L);
    }

    @Test
    public void testGetFloat() throws Exception {

    }

    @Test
    public void testPutFloat() throws Exception {

    }

    @Test
    public void testGetString() throws Exception {

    }

    @Test
    public void testPutString() throws Exception {

    }
}