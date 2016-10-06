package com.example.sjors.sjors_witteveen_pset5;

import android.app.Fragment;
import android.content.Intent;
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

public class ToDoListFragment extends Fragment {

    private EditText addItem;
    private ListView toDoListView;

    private ToDoManager toDoManager;

    private MyAdapter adapter;

    // links to layout file and find views by id
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        // find Views by id
        addItem = (EditText) view.findViewById(R.id.add_item);
        toDoListView = (ListView) view.findViewById(R.id.to_do_list);

        return view;
    }

    // sets adapter and handles on click listeners
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toDoManager = ToDoManager.getInstance();

        // get list position extra from previous activity
        Intent intent = getActivity().getIntent();
        final int listPosition = intent.getExtras().getInt("position");

        // initializes new adapter with the right ToDoItems and sets to toDoListView
        adapter = new MyAdapter(getActivity(),
                toDoManager.getToDoLists().get(listPosition).getToDoItems());
        toDoListView.setAdapter(adapter);

        // sets activity title to ToDoList title
        getActivity().setTitle(toDoManager.getToDoLists().get(listPosition).getTitle());

        // listens to EditText editor action
        addItem.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            // initializes new ToDoItem and adds it to ArrayList of ToDoItems
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ToDoItem toDoItem = new ToDoItem(addItem.getText().toString());
                    toDoManager.getToDoLists().get(listPosition).getToDoItems().add(toDoItem);
                    adapter.notifyDataSetChanged();
                    addItem.getText().clear();
                    toDoListView.smoothScrollToPosition(adapter.getCount() - 1);
                    return true;
                }
                return true;
            }
        });

        // toDoListView on item click listener
        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // checks/unchecks ToDoItem that is clicked and notifies adapter
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toDoManager.getToDoLists().get(listPosition).getToDoItems()
                        .get(position).switchChecked();
                adapter.notifyDataSetChanged();
            }
        });

        // toDoListView on item long click listener
        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // removes ToDoItem from ToDoItems ArrayList and notifies adapter
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoManager.getToDoLists().get(listPosition).getToDoItems().remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

}
