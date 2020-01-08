package com.example.movingcompanymanagement.modal;

import android.util.Log;

import java.util.Comparator;

public class DateComparator implements Comparator<TaskData> {

    @Override
    public int compare(TaskData task1, TaskData task2) {
        Log.i("sort", task1.getTask_date());
        Log.i("sort", task2.getTask_date());
        return task1.getTask_date().compareTo(task2.getTask_date());
    }
}
