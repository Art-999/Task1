package com.example.arturmusayelyan.task1;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 27/11/2017.
 */

public class DataBase {
    private static DataBase instance;
    private ArrayList<Person> personsList;
    private ArrayList<Message> messageHistoryList;

    public ArrayList<Message> getMessageHistoryList() {
        return messageHistoryList;
    }

    public ArrayList<Person> getPersonsList() {
        return personsList;
    }

    //narek
    // private HashMap<String, HashMap<String, ArrayList<Message>>> messageHashMap;


    private DataBase() {

    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    //karanq arden static keyworde hanenq vor darna singleton
    public void addPerson(Person person) {
        if (personsList == null) {
            personsList = new ArrayList<>();
        }
        personsList.add(person);
    }

    //karanq arden static keyworde hanenq vor darna singleton
    public void addMessageToHistory(Message message) {
        if (messageHistoryList == null) {
            messageHistoryList = new ArrayList<>();
        }
        messageHistoryList.add(message);
    }
}
