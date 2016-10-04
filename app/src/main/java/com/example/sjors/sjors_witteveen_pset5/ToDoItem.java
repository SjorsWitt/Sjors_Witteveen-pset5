/*
 * By Sjors Witteveen
 * A ToDoItem object consists of an int id, a String text and a boolean checked.
 * The int id is the index in in the SQLiteDatabase. The String text is the to-do item text that the
 * user wants to save. Finally, the boolean checked is true when the to-do item is checked.
 */

package com.example.sjors.sjors_witteveen_pset5;

public class ToDoItem {

    public int id;
    public String text;
    public boolean checked;

    // constructor: ToDoItem is unchecked by default
    public ToDoItem(String text) {
        this.text = text;
        this.checked = false;
    }

    // constructor
    public ToDoItem(int id, String text, Boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    // checks/unchecks depending on whether already checked or not
    // returns this ToDoItem
    public ToDoItem switchChecked() {
        checked = !checked;
        return this;
    }
}
