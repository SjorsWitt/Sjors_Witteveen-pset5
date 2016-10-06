package com.example.sjors.sjors_witteveen_pset5;

import java.util.ArrayList;

public class ToDoList {

    private String title;
    private ArrayList<ToDoItem> toDoItems = new ArrayList<>();

    public ToDoList(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

}
