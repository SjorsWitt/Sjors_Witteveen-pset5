/*
 * by Sjors Witteveen
 * A ToDoList consists of a title String and an ArrayList of ToDoItem objects
 */

package com.example.sjors.sjors_witteveen_pset5;

import java.util.ArrayList;

public class ToDoList {

    private String title;
    private ArrayList<ToDoItem> toDoItems = new ArrayList<>();

    // constructor when new ToDoList is made: ArrayList is empty
    public ToDoList(String title) {
        this.title = title;
    }

    // constructor to construct ToDoList objects from the database
    public ToDoList(String title, ArrayList<ToDoItem> toDoItems) {
        this.title = title;
        this.toDoItems = toDoItems;
    }

    // returns title
    public String getTitle() {
        return title;
    }

    // returns ToDoItem ArrayList
    public ArrayList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

}
