package com.example.arturmusayelyan.task1;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 27/11/2017.
 */

public class DataBase {
    static ArrayList<Person> personsList;
    static ArrayList<Message> messageHistoryList;

    public static void addPerson(Person person) {
        if (personsList == null) {
            personsList = new ArrayList<>();
        }
        personsList.add(person);
    }

    public static void addMessageToHistory(Message message) {
        if (messageHistoryList == null) {
            messageHistoryList = new ArrayList<>();
        }
        messageHistoryList.add(message);
    }
}
