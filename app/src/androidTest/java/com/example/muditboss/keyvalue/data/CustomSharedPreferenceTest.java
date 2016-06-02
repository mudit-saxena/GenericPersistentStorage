package com.example.muditboss.keyvalue.data;


import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

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

        // Insert the key value pair for primitive data types

        csm.put("genericTest",98);
        csm.put("genericFloat",0.25f);
        csm.put("genericLong",98764646431L);
        csm.put("genericString","Mudit");

        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(98);
        ar.add(100);

        HashSet<String> hs = new HashSet<>();
        hs.add("Key");
        hs.add("Value");

        LinkedList<Integer> ll = new LinkedList<>();
        ll.add(1);
        ll.add(34);

        // Insert key value for different class objects
        csm.put("Array",ar);
        csm.put("HashSet",hs);
        csm.put("LinkedList",ll);

        // Check whether value inserted or not?
        ArrayList ae = csm.get("Array");
        HashSet he = csm.get("HashSet");
        LinkedList le = csm.get("LinkedList");

        assertEquals(100,ae.get(1));
        assertTrue(he.contains("Key"));
        assertEquals(34,le.get(1));
        assertEquals(98,csm.get("genericTest"));

        // Update the existing value
        csm.put("genericTest",120);

        assertEquals(120,csm.get("genericTest"));

        assertEquals(0.25f,csm.get("genericFloat"));

        assertEquals(98764646431L,csm.get("genericLong"));



    }

}