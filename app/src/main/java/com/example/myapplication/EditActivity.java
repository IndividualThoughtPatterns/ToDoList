package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;



public class EditActivity extends AppCompatActivity {
    String taskDescription = "";
    String notesDescription = "";

    String deadlineDate = "";
    String deadlineTime = "";
    int taskId;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor taskCursor;
    EditText taskItem;
    EditText notesItem;
    DatePicker deadlineDateItem;
    TimePicker deadlineTimeItem;

    String[] dateElems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle extras = getIntent().getExtras();

        taskItem = findViewById(R.id.task);
        notesItem = findViewById(R.id.notes);
        deadlineDateItem = findViewById(R.id.deadlineDate);
        deadlineTimeItem = findViewById(R.id.deadlineTime);

        if (extras != null) {
            taskId = extras.getInt("id");
        }

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        taskCursor = db.rawQuery(
                "SELECT * FROM tasks " + "WHERE _id = " + taskId + ";"
                , null);

        if (taskCursor.moveToFirst()) {
            if (taskCursor.getString(1) != null) {
                taskDescription = taskCursor.getString(1);
                taskItem.setText(taskDescription);
            }

            if (taskCursor.getString(2) != null) {
                notesDescription = taskCursor.getString(2);
                notesItem.setText(notesDescription);
            }
            if (taskCursor.getString(3) != null) {
                dateElems = taskCursor.getString(3).split("/");
                deadlineDateItem.updateDate(
                        deadlineDateItem.getYear(),
                        deadlineDateItem.getMonth(),
                        deadlineDateItem.getDayOfMonth() + 1
                );

                if (dateElems.length > 1) {
                  deadlineDateItem.updateDate(
                          Integer.valueOf(dateElems[0]),
                          Integer.valueOf(dateElems[1]),
                          Integer.valueOf(dateElems[2])
                  );
                }
            }
            if (taskCursor.getString(4) != null) {
                String[] timeElems = taskCursor.getString(4).split(":");
                if (timeElems.length > 1) {
                    deadlineTimeItem.setHour(Integer.valueOf(timeElems[0]));
                    deadlineTimeItem.setMinute(Integer.valueOf(timeElems[1]));
                }
            }
        }

        taskCursor.close();

        taskItem.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskDescription = s.toString();
            }
        });

        notesItem.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notesDescription = s.toString();
            }
        });
    }

    public void addNote(View view) {
        deadlineTime = deadlineTimeItem.getHour() + ":" + deadlineTimeItem.getMinute();

        deadlineDate = deadlineDateItem.getYear()
                + "/" + deadlineDateItem.getMonth()
                + "/" + deadlineDateItem.getDayOfMonth();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, taskDescription);
        cv.put(DatabaseHelper.COLUMN_NOTES, notesDescription);
        cv.put(DatabaseHelper.COLUMN_DEADLINE_DATE, deadlineDate);
        cv.put(DatabaseHelper.COLUMN_DEADLINE_TIME, deadlineTime);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            taskId = extras.getInt("id");
            Log.d("rows?", String.valueOf(
                    db.update(
                            DatabaseHelper.TABLE,
                            cv,
                            DatabaseHelper.COLUMN_ID + "=" + taskId,
                            null
                    ))
            );
        } else {
            Log.d("rows moved: ", String.valueOf(db.insert(DatabaseHelper.TABLE, null, cv)));
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
