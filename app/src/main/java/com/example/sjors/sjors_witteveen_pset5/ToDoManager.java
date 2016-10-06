package com.example.sjors.sjors_witteveen_pset5;

import java.util.ArrayList;

public class ToDoManager {

    private ArrayList<ToDoList> toDoLists = new ArrayList<>();

    private static ToDoManager ourInstance = new ToDoManager();

    public static ToDoManager getInstance() {
        return ourInstance;
    }

    private ToDoManager() {
    }

    public ToDoList getToDoList(int position) {
        return toDoLists.get(position);
    }

    public void addToDoList(ToDoList toDoList) {
        toDoLists.add(toDoList);
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < toDoLists.size(); i++) {
            titles.add(toDoLists.get(i).getTitle());
        }

        return titles;
    }

}
