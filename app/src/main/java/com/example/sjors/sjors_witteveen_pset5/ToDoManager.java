/*
 * by Sjors Witteveen
 * ToDoManager singleton is used to remember the ToDoList objects throughout the app
 */

package com.example.sjors.sjors_witteveen_pset5;

import java.util.ArrayList;

public class ToDoManager {

    private ArrayList<ToDoList> toDoLists = new ArrayList<>();

    // returns instance of this singleton ToDoManager
    private static ToDoManager ourInstance = new ToDoManager();

    public static ToDoManager getInstance() {
        return ourInstance;
    }

    // constructor
    private ToDoManager() {
    }

    // returns ToDoList ArrayList
    public ArrayList<ToDoList> getToDoLists() {
        return toDoLists;
    }

    // returns all ToDoList titles
    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < toDoLists.size(); i++) {
            titles.add(toDoLists.get(i).getTitle());
        }
        return titles;
    }

    public void clear() {
        toDoLists = new ArrayList<>();
    }

}
