package com.example.arturmusayelyan.task1;

/**
 * Created by artur.musayelyan on 04/12/2017.
 */

public final class PersonDbBaseInfo {
    public PersonDbBaseInfo() {

    }

    public static abstract class PersonEntry {
        public static final String TABLE_NAME = "person_table";

        public static final String FIRSTNAME = "firstName";
        public static final String LASTNAME = "lastName";
        public static final String USERNAME = "userName";
        public static final String PASSWORD = "password";
        public static final String PARENTUSERNAME = "parentUserName";
    }
}
