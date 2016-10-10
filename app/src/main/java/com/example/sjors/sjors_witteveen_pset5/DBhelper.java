/*
 * By Sjors Witteveen
 * Extension of SQLiteOpenHelper. This class creates and manages a table consisting of ToDoItem
 * objects. create and read methods are implemented in this class.
 */

package com.example.sjors.sjors_witteveen_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // table/column names
    private String table_name;
    private String _id = "_id";
    private String to_do_text = "to_do_text";
    private String checked = "checked";

    // constructor
    public DBhelper(Context context, String table_name) {
        super(context, table_name, null, DATABASE_VERSION);
        this.table_name = table_name;
    }

    // creates table with an integer, a text and a boolean
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "to_do_text TEXT, checked BOOLEAN )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + table_name;
        db.execSQL(query);

        onCreate(db);
    }

    // puts a ToDoItem in the table
    public void create(ToDoItem toDoItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(to_do_text, toDoItem.getText());
        values.put(checked, toDoItem.getChecked());

        db.insert(table_name, null, values);
        db.close();
    }

    // reads table and returns ToDoItems in an ArrayList
    public ArrayList<ToDoItem> read() {
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT _id , to_do_text , checked FROM " + table_name;
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

}
