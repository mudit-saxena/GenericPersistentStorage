package com.example.muditboss.keyvalue.data;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for CustomSharedPreference
 * All methods are tested
 */

public class CustomSharedPreferenceTest extends AndroidTestCase{

    RenamingDelegatingContext context;
    CustomSharedPreference csm;


    @Before
    public void setUp() throws Exception {

        super.setUp();
        context = new RenamingDelegatingContext(getContext(),"_test");
        // Check whether context is not null
        assertNotNull(context);

        csm = new CustomSharedPreference(context);
        //Check whether csm object is not null
        assertNotNull(csm);
    }

    @Test
    public void testPut() throws Exception {

        // Insert the key value pair

        csm.put("genericTest",98);
        csm.put("genericFloat",0.25f);
        csm.put("genericLong",98764646431L);
        csm.put("genericString","stringVal");

        // Check whether value inserted or not?
        assertEquals(98,csm.get("genericTest"));

        assertEquals(0.25f,csm.get("genericFloat"));

        assertEquals(98764646431L,csm.get("genericLong"));

        assertEquals("stringVal",csm.get("genericString"));

    }

}