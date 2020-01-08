package com.example.movingcompanymanagement.modal;

import java.util.Comparator;

public class DateComparator implements Comparator<TaskData> {

    @Override
    public int compare(TaskData task1, TaskData task2) {
        return task1.getTask_date().compareTo(task2.getTask_date());
    }
}
