package com.salik.todoapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.salik.todoapp.adapter.ToDoAdapter;
import com.salik.todoapp.data.DataBaseHandler;
import com.salik.todoapp.model.ToDo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView toDoRecycler;
    private  ToDoAdapter toDoAdapter;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    private EditText enterTodo;
    List<ToDo> todos;
    Button saveButton;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toDoRecycler = findViewById(R.id.todo_recycler);

        DataBaseHandler db = new DataBaseHandler(this);
        toDoRecycler.setLayoutManager(new LinearLayoutManager(this));
        todos = new ArrayList<>();
        todos =  db.getAllToDos();
        toDoAdapter = new ToDoAdapter(this,todos);
        toDoRecycler.setAdapter(toDoAdapter);
        toDoAdapter.notifyDataSetChanged();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();

            }
        });
    }

    private void showPopUp() {

        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.edit_todo,null);
        enterTodo = view.findViewById(R.id.enter_todo);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo todo = new ToDo();
                todo.setTitle(enterTodo.getText().toString());
                todo.setCompleted(false);
                todo = saveItem(todo);
                todos.add(todo);
                alertDialog.dismiss();


            }
        });
        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    private ToDo saveItem(ToDo todo) {
        DataBaseHandler db = new DataBaseHandler(this);
         todo = db.addToDo(todo);
         return todo;


    }

}