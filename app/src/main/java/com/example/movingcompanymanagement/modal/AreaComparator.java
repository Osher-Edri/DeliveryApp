package com.example.movingcompanymanagement.modal;

import java.util.Comparator;

public class AreaComparator implements Comparator<TaskData> {
    @Override
    public int compare(TaskData task1, TaskData task2) {
        return task1.getArea().compareTo(task2.getArea());
    }
}
