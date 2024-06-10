package com.example.myapplication;

public class Task {
    private int _id;
    private String description;
    private String notes;
    private String deadline_date;
    private String deadline_time;

    private boolean isChecked;
    public Task(
            int _id,
            String description,
            String notes,
            String deadline_date,
            String deadline_time,
            boolean isChecked
    ) {
        this._id = _id;
        this.description = description;
        this.notes = notes;
        this.deadline_date = deadline_date;
        this.deadline_time = deadline_time;
        this.isChecked = false;
    }

    public int getId() {
        return this._id;
    }
    public void setId(int _id) {
        this._id = _id;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getNotes(String notes) {
        return this.notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getDeadline_date() {
        return this.deadline_date;
    }
    public void setDeadline_date(String deadlineDate) {
        this.deadline_date = deadlineDate;
    }
    public String getDeadline_time() {
        return this.deadline_time;
    }
    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
