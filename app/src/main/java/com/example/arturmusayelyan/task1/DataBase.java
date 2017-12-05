package com.example.arturmusayelyan.task1;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 27/11/2017.
 */

public class DataBase {
    private static DataBase instance;
    static ArrayList<Person> personsList;
    static ArrayList<Message> messageHistoryList;

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
