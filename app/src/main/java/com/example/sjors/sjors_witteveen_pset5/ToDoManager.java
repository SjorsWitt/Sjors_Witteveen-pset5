package com.example.sjors.sjors_witteveen_pset5;

public class ToDoManager {

    private static ToDoManager ourInstance = new ToDoManager();

    public static ToDoManager getInstance() {
        return ourInstance;
    }

    private ToDoManager() {
    }


}
