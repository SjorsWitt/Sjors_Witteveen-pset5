/*
 * By Sjors Witteveen
 * Extension of SQLiteOpenHelper. This class creates and manages a table consisting of ToDoItem
 * objects. CRUD (create, read, update & delete) methods are implemented in this class.
 */

package com.example.sjors.sjors_witteveen_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "firstdb.db";
    private static final int DATABASE_VERSION = 1;

    // table/column names
    private String to_do_items = "to_do_items";
    private String _id = "_id";
    private String to_do_text = "to_do_text";
    private String checked = "checked";

    // constructor
    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creates table with an integer, a text and a boolean
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE to_do_items ( _id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "to_do_text TEXT, checked BOOLEAN )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS to_do_items";
        db.execSQL(query);

        onCreate(db);
    }

    // puts a ToDoItem in the to_do_items table
    public void create(ToDoItem toDoItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(to_do_text, toDoItem.getText());
        values.put(checked, toDoItem.getChecked());

        db.insert(to_do_items, null, values);
        db.close();
    }

    // reads to_do_items table and returns ToDoItems in an ArrayList
    public ArrayList<ToDoItem> read() {
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT _id , to_do_text , checked FROM to_do_items";
        Cursor cursor = db.rawQuery(query, null);

        // loop through cursor
        while (cursor.moveToNext()) {
            int id_int = cursor.getInt(cursor.getColumnIndex(_id));
            String to_do_text_string = cursor.getString(cursor.getColumnIndex(to_do_text));
            Boolean checked_boolean = cursor.getInt(cursor.getColumnIndex(checked)) > 0;
            ToDoItem toDoItem = new ToDoItem(id_int, to_do_text_string, checked_boolean);
            toDoItems.add(toDoItem);
        }

        cursor.close();
        db.close();

        return toDoItems;
    }

    // updates ToDoItem checked/unchecked in to_do_items table
    public void update(ToDoItem toDoItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(checked, toDoItem.getChecked());

        db.update(to_do_items, values, _id + " = ?",
                new String[]{String.valueOf(toDoItem.getId())});

        db.close();
    }

    // deletes ToDoItem from to_do_items table
    public void delete(ToDoItem toDoItem) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(to_do_items, _id + " = ?", new String[]{String.valueOf(toDoItem.getId())});

        db.close();
    }

}
