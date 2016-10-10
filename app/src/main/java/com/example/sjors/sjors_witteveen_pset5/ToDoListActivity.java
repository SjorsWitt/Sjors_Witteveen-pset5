/*
 * by Sjors Witteveen
 * This Activity sets up the activity bar buttons. The activity's layout consists of the fragment
 * ToDoListFragment
 */

package com.example.sjors.sjors_witteveen_pset5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ToDoListActivity extends AppCompatActivity {

    // onCreate method sets layout and displays navigation button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        // display up navigation button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // navigate up when top left arrow is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToDoManager toDoManager = ToDoManager.getInstance();
        SharedPreferences pref = getSharedPreferences("table names", MODE_PRIVATE);
        DBhelper dBhelper;

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
