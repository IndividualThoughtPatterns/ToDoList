package com.example.myapplication;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.res.Resources;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.CheckBox;

import android.util.SparseBooleanArray;


public class MainActivity extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<Task>();
    RecyclerView taskList;
    TaskAdapter taskAdapter;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    static Button applyChangesBtn;
    static Button discardChangesBtn;
    static Button createTask;
    static TextView dummySpace;
    TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);;
        db = databaseHelper.getWritableDatabase();

        emptyList = findViewById(R.id.emptyList);
        setInitialData();

        taskList = findViewById(R.id.taskList);
        applyChangesBtn = findViewById(R.id.applyChanges);
        discardChangesBtn = findViewById(R.id.discardChanges);
        createTask = findViewById(R.id.createTask);
        dummySpace = findViewById(R.id.dummySpace);

        TaskAdapter.OnTaskClickListener taskClickListener = new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task, int position){
                discardChanges(discardChangesBtn);

                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                int idToPut = tasks.get(position).getId();

                intent.putExtra("id", idToPut);
                startActivity(intent);
            }
        };

        taskAdapter = new TaskAdapter(this, tasks, taskClickListener);
        taskList.setAdapter(taskAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setInitialData() {
        //getBaseContext().deleteDatabase("app.db");

        Cursor query = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE  +";", null);

        while(query.moveToNext()) {
            emptyList.setVisibility(View.GONE);

            String description = query.getString(1);
            String notes = query.getString(2);
            String deadline_date = query.getString(3);
            String deadline_time = query.getString(4);

            String[] timeNormalizerArray = deadline_time.split(":");
            if (timeNormalizerArray[0].length() == 1) {
                timeNormalizerArray[0] = "0" + timeNormalizerArray[0];
            }
            if (timeNormalizerArray[1].length() == 1) {
                timeNormalizerArray[1] = "0" + timeNormalizerArray[1];
            }
            deadline_time = String.join(":", timeNormalizerArray);

            String[] dateNormalizerArray = deadline_date.split("/");
            dateNormalizerArray[1] = String.valueOf(Integer.parseInt(dateNormalizerArray[1]) + 1);
            deadline_date = "Выполнить до:  " + String.join("/", dateNormalizerArray);

            tasks.add(
                    new Task(
                            query.getInt(0),
                            description,
                            //"",
                            notes,
                            deadline_date,
                            deadline_time,
                            false
                    )
            );
        }

        db.close();
    }

    public void switchToEditPage(View view) {
        discardChanges(view);
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public static void toggleEditButtons(boolean hasChangesToApply) {
        if (hasChangesToApply) {
            applyChangesBtn.setVisibility(View.VISIBLE);
            discardChangesBtn.setVisibility(View.VISIBLE);
            createTask.setVisibility(View.GONE);
            dummySpace.setVisibility(View.GONE);
        } else {
            applyChangesBtn.setVisibility(View.GONE);
            discardChangesBtn.setVisibility(View.GONE);
            createTask.setVisibility(View.VISIBLE);
            dummySpace.setVisibility(View.VISIBLE);
        }
    }

    public void discardChanges(View view) {
        toggleEditButtons(false);
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setChecked(false);
        }
        taskAdapter.notifyDataSetChanged();
    }

   public void applyChanges(View view) {
       db = databaseHelper.getWritableDatabase();

        for (int i = 0; i < tasks.size(); i++ ) {
            if (tasks.get(i).isChecked()) {
                Log.d("rows?", String.valueOf(
                    db.delete(DatabaseHelper.TABLE, "_id = ?",
                        new String[]{
                            String.valueOf(tasks.get(i).getId()
                            )
                        })
                    )
                );
            }
        }

        db.close();
        recreate();
   }
}

