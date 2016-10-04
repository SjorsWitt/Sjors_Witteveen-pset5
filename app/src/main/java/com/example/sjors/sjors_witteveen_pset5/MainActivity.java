/*
 * by Sjors Witteveen
 * This is the main app activity. This app is a simple To-Do List. The user can add new to-do items
 * by typing them in the bottom textfield. Once added, the items appear in the ListView with an
 * unchecked box. The user can now check this item when the to-do item has been done. The user can
 * also choose to remove the to-do item completely by long-pressing an item. This class initializes
 * MyAdapter and sets it to the ListView toDoList. It also handles OnEditor and OnClick listeners.
 */

package com.example.sjors.sjors_witteveen_pset5;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String FIRST_RUN = "first run";

    private DBhelper dbhelper;

    private EditText addItem;
    private ListView toDoList;

    private MyAdapter adapter;

    // onCreate method handles variable initializations and onClick methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find Views by id
        addItem = (EditText) findViewById(R.id.add_item);
        toDoList = (ListView) findViewById(R.id.to_do_list);

        // DBhelper initialization
        dbhelper = new DBhelper(this);

        // creates three new ToDoItem objects only on first run
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        if (pref.getBoolean(FIRST_RUN, true)) {
            dbhelper.create(new ToDoItem("Welcome to your To-Do List!"));
            dbhelper.create(new ToDoItem("Type a new to-do item below."));
            dbhelper.create(new ToDoItem("Long-press an item to remove it."));
            pref.edit().putBoolean(FIRST_RUN, false).apply();
        }

        // adapter initialization and adapter is set to ListView toDoList
        adapter = new MyAdapter(this, dbhelper.read());
        toDoList.setAdapter(adapter);

        // listens to EditText editor action
        addItem.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            // initializes new ToDoItem, puts it in SQLiteDataBase table & notifies adapter
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ToDoItem toDoItem = new ToDoItem(addItem.getText().toString());
                    dbhelper.create(toDoItem);
                    adapter.clear();
                    adapter.addAll(dbhelper.read());
                    addItem.getText().clear();
                    return true;
                }
                return true;
            }
        });

        // toDoList on item click listener
        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // checks/unchecks ToDoItem in adapter resource, updates in SQLiteDataBase table
            // & notifies adapter
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbhelper.update(adapter.getItem(position).switchChecked());

                adapter.notifyDataSetChanged();
            }
        });

        // toDoList on item long click listener
        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // deletes ToDoItem from SQLiteDataBase, removes it from adapter resource
            // & notifies adapter
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbhelper.delete(adapter.getItem(position));

                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

                return true;
            }
        });

    }
}
