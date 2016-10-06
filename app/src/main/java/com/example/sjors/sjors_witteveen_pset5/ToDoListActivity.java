/*
 * by Sjors Witteveen
 * This is the main app activity. This app is a simple To-Do List. The user can add new to-do items
 * by typing them in the bottom textfield. Once added, the items appear in the ListView with an
 * unchecked box. The user can now check this item when the to-do item has been done. The user can
 * also choose to remove the to-do item completely by long-pressing an item. This class initializes
 * MyAdapter and sets it to the ListView toDoList. It also handles OnEditor and OnClick listeners.
 */

package com.example.sjors.sjors_witteveen_pset5;

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
    protected void onStop() {
        super.onStop();
        
    }
}
