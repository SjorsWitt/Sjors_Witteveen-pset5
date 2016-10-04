package com.example.sjors.sjors_witteveen_pset5;

import java.util.ArrayList;

public class ToDoList {

    private String title;
    private ArrayList<ToDoItem> toDoItems;

    public ToDoList(String title, ArrayList<ToDoItem> toDoItems) {
        this.title = title;
        this.toDoItems = toDoItems;
    }
}
