package com.salik.todoapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salik.todoapp.R;
import com.salik.todoapp.data.DataBaseHandler;
import com.salik.todoapp.model.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {


    private  List<ToDo> todos;
    private  Context context;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        private TextView todoTitle;
        private CheckBox todoCompleted;
        private Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoTitle = itemView.findViewById(R.id.todo_title);
            todoCompleted = itemView.findViewById(R.id.todo_completed);
            deleteButton = itemView.findViewById(R.id.delete_btn);
            deleteButton.setOnClickListener(this);
            todoCompleted.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()){
                case R.id.delete_btn:
                    position = getAdapterPosition();
                    ToDo todo = todos.get(position);
                    deleteItem(todo.getId());
                    break;
            }
        }

        private void deleteItem(int id) {
            DataBaseHandler db = new DataBaseHandler(context);
            db.deleteToDo(id);
            todos.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.todo_completed:
                    ToDo todo = todos.get(getAdapterPosition());
                    todo.setCompleted(isChecked);
                    Log.d("NewItem",todo.toString());
                    DataBaseHandler db = new DataBaseHandler(context);
                    db.updateToDo(todo);
            }

        }
    }


}
