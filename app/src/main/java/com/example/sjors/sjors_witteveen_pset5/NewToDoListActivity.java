package com.example.sjors.sjors_witteveen_pset5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NewToDoListActivity extends AppCompatActivity {

    private ToDoManager toDoManager;

    private EditText titleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_to_do_list_activity);

        toDoManager = ToDoManager.getInstance();

        titleInput = (EditText) findViewById(R.id.title_input);
    }

    public void onSave(View view) {
        toDoManager.addToDoList(new ToDoList(titleInput.getText().toString()));
        finish();
    }
}
