package com.example.arturmusayelyan.task1;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 27/11/2017.
 */

public class DataBase {
    static ArrayList<Person> personsList;

    public static void addPerson(Person person) {
        if (personsList == null) {
            personsList = new ArrayList<>();
        }
        personsList.add(person);
    }
}
