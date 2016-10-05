package com.example.sjors.sjors_witteveen_pset5;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class ToDoListFragment extends Fragment {

    private final String FIRST_RUN = "first run";

    private EditText addItem;
    private ListView toDoList;

    private DBhelper dbhelper;

    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        // find Views by id
        addItem = (EditText) view.findViewById(R.id.add_item);
        toDoList = (ListView) view.findViewById(R.id.to_do_list);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DBhelper initialization
        dbhelper = new DBhelper(getActivity());

        // adapter initialization and adapter is set to ListView toDoList
        adapter = new MyAdapter(getActivity(), dbhelper.read());
        toDoList.setAdapter(adapter);

        // creates three new ToDoItem objects only on first run
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        if (pref.getBoolean(FIRST_RUN, true)) {
            dbhelper.create(new ToDoItem("Welcome to your To-Do List!"));
            dbhelper.create(new ToDoItem("Type a new to-do item below."));
            dbhelper.create(new ToDoItem("Long-press an item to remove it."));
            pref.edit().putBoolean(FIRST_RUN, false).apply();
        }

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
                    toDoList.smoothScrollToPosition(adapter.getCount() - 1);
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
