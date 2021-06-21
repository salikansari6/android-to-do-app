package com.salik.todoapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.salik.todoapp.adapter.ToDoAdapter;
import com.salik.todoapp.data.DataBaseHandler;
import com.salik.todoapp.model.ToDo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView toDoRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toDoRecycler = findViewById(R.id.todo_recycler);
        DataBaseHandler db = new DataBaseHandler(this);
        List<ToDo> todos =  db.getAllToDos();

        toDoRecycler.setLayoutManager(new LinearLayoutManager(this));
        toDoRecycler.setAdapter(new ToDoAdapter(todos));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}