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
    public void testPutInt() throws Exception {

        csm.putInt("intTestKey1", 4);
        csm.putInt("intTestKey2", 5);

        // Value corresponding to Key should be 4 otherwise it fails
        assertEquals(4,csm.getInt("intTestKey1"));

        // Update value for key "intTestKey1"
        csm.putInt("intTestKey1",100);

        //Check whether value updated
        assertEquals(100,csm.getInt("intTestKey1"));

        // assertion fails because Key cannot be duplicate, Primary Key constraint satisfied
        assertFalse("Error:Duplicate key",csm.putFloat("intTestKey1",0.25f));
    }

    @Test
    public void testPutLong() throws Exception {

        csm.putLong("longTestKey1", 100L);
        csm.putLong("longTestKey2", 200L);

        // Value corresponding to Key should be 100L otherwise it fails
        assertEquals(100L,csm.getLong("longTestKey1"));

        // Update value for key "longTestKey1"
        csm.putLong("longTestKey1",500L);

        //Check whether value updated
        assertEquals(500L,csm.getLong("longTestKey1"));

        // assertion fails because Key cannot be duplicate, Primary Key constraint satisfied
        assertFalse("Error:Duplicate key",csm.putFloat("longTestKey1",0.25f));
    }

    @Test
    public void testPutFloat() throws Exception {

        csm.putFloat("floatTestKey1",2.325f);
        csm.putFloat("floatTestKey2",9.25f);

        // Value corresponding to Key should be 2.325f otherwise it fails
        assertEquals(2.325f,csm.getFloat("floatTestKey1"));

        // Update value for key "floatTestKey1"
        csm.putFloat("floatTestKey1",0.5f);

        //Check whether value updated
        assertEquals(0.5f,csm.getFloat("floatTestKey1"));

        // assertion fails because Key cannot be duplicate, Primary Key constraint satisfied
        assertFalse("Error:Duplicate key",csm.putInt("floatTestKey1",25));
    }

    @Test
    public void testPutString() throws Exception {

        csm.putString("stringTestKey1","Test1");
        csm.putString("stringTestKey2","Test2");

        // Value corresponding to Key should be Test1 otherwise it fails
        assertEquals("Test1",csm.getString("stringTestKey1"));

        // Update value for key "stringTestKey1"
        csm.putString("stringTestKey1","Mudit");

        //Check whether value updated
        assertEquals("Mudit",csm.getString("stringTestKey1"));

        // assertion fails because Key cannot be duplicate, Primary Key constraint satisfied
        assertFalse("Error:Duplicate key",csm.putLong("stringTestKey1",2500L));
    }
}