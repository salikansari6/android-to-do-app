package com.salik.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salik.todoapp.R;
import com.salik.todoapp.model.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {


    private final List<ToDo> todos;
    private  final Context context;
    public ToDoAdapter(Context context,List<ToDo> todos) {
        this.todos = todos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.todoTitle.setText(String.valueOf(todos.get(position).getTitle()));
        holder.todoCompleted.setChecked(todos.get(position).isCompleted());
    }


    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView todoTitle;
        private CheckBox todoCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todoCompleted = itemView.findViewById(R.id.todo_completed);
        }
    }

}
