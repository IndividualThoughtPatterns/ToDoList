package com.example.myapplication;
import  android.content.Context;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    interface OnTaskClickListener{
        void onTaskClick(Task task, int position);
    }

    private final OnTaskClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<Task> tasks;
    private int layout;
    MainActivity mainActivity = new MainActivity();

    TaskAdapter(Context context, List<Task> tasks, OnTaskClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.description.setText(task.getDescription());
        holder.deadlineDate.setText(task.getDeadline_date() + "  |  ");
        holder.deadlineTime.setText(task.getDeadline_time());
        holder.checkbox.setChecked(task.isChecked());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onTaskClick(task, position);
            }
        });

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();

                if (isChecked) {
                    tasks.get(holder.getAdapterPosition()).setChecked(true);
                    mainActivity.toggleEditButtons(true);
                } else {
                    tasks.get(holder.getAdapterPosition()).setChecked(false);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description, deadlineDate, deadlineTime;
        CheckBox checkbox;
        ViewHolder(View view){
            super(view);
            description = view.findViewById(R.id.description);
            deadlineDate  = view.findViewById(R.id.deadlineDate);
            deadlineTime = view.findViewById(R.id.deadlineTime);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }
}