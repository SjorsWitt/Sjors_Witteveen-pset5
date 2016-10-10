/*
 * by Sjors Witteveen
 * This is the main app activity. With this app you can create multiple to-do lists. The user can
 * create new to-do lists and add new to-do items to this list. This class manages all data by
 * reading from the data base onCreate and saving to new databases onStop. The activity's layout
 * consists of the fragment ToDoListsFragment.
 */

package com.example.sjors.sjors_witteveen_pset5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ToDoManager toDoManager;
    private SharedPreferences pref;
    private DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoManager = ToDoManager.getInstance();
        if (toDoManager.getTitles().size() != 0) {
            toDoManager.clear();
        }
        pref = getSharedPreferences("table names", MODE_PRIVATE);

        // for all tableName: read SQLite Database and add to toDoManager
        int numberOfTitles = pref.getAll().size();
        for (int i = 0; i < numberOfTitles; i++) {
            String tableName = pref.getString(String.valueOf(i), "");
            dBhelper = new DBhelper(this, tableName);
            String title = tableName.replaceAll("_", " ");
            ToDoList toDoList = new ToDoList(title, dBhelper.read());
            toDoManager.getToDoLists().add(toDoList);
        }
    }

    // show add_new button (plus icon) in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new_button, menu);
        return true;
    }

    // create new To-Do List when add_new button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_new) {
            Intent intent = new Intent(this, NewToDoListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    // create new Databases with new data
    @Override
    protected void onPause() {
        super.onPause();
        for (String db : databaseList()) {
            deleteDatabase(db);
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();

        // for every ToDoList in singleton toDoManager: make new DBhelper with custom tableName
        for (int i = 0; i < toDoManager.getToDoLists().size(); i++) {
            String title = toDoManager.getToDoLists().get(i).getTitle();
            String tableName = title.replaceAll(" ", "_");

            dBhelper = new DBhelper(this, tableName);

            // saves all tableNames
            editor.putString(String.valueOf(i), tableName);

            // for every ToDoItem in ToDoList: create ToDoItem in SQLite Database table
            for (int j = 0; j < toDoManager.getToDoLists().get(i).getToDoItems().size(); j++) {
                dBhelper.create(toDoManager.getToDoLists().get(i).getToDoItems().get(j));
            }
        }
        editor.apply();
    }

}
