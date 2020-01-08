package com.example.movingcompanymanagement.modal;

import android.util.Log;

import java.util.Comparator;

public class DateComparator implements Comparator<TaskData> {

    @Override
    public int compare(TaskData task1, TaskData task2) {
        if(task1.getTaskYear() != task2.getTaskYear())
            return task1.getTaskYear() - task2.getTaskYear();
        else if(task1.getTaskMonth() != task2.getTaskMonth())
            return task1.getTaskMonth() - task2.getTaskMonth();
        return task1.getTaskDay() - task2.getTaskDay();
    }
}
