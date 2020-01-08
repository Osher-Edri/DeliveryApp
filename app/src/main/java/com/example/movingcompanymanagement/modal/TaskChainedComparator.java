package com.example.movingcompanymanagement.modal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TaskChainedComparator implements Comparator<TaskData> {

    private List<Comparator<TaskData>> comparatorList;

    @SafeVarargs
    public TaskChainedComparator(Comparator<TaskData>... comparators){
        this.comparatorList = Arrays.asList(comparators);
    }


    @Override
    public int compare(TaskData t1, TaskData t2) {
        for (Comparator<TaskData> comparator : comparatorList){
            int result = comparator.compare(t1,t2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
