package com.example.sjors.sjors_witteveen_pset5;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToDoListsFragment  extends Fragment {

    private ToDoManager toDoManager;

    private ListView toDoLists;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_lists, container, false);

        toDoLists = (ListView) view.findViewById(R.id.to_do_lists);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toDoManager = ToDoManager.getInstance();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                toDoManager.getTitles());
        toDoLists.setAdapter(adapter);

        // toDoLists on item click listener
        toDoLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ToDoListActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(toDoManager.getTitles());
        adapter.notifyDataSetChanged();
    }
}