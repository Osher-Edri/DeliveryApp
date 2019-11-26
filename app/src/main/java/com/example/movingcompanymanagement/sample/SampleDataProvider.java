package com.example.movingcompanymanagement.sample;

import com.example.movingcompanymanagement.modal.TaskData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<TaskData> tasks_list;
    public static Map<String, TaskData> tasks_map;


    static {
        tasks_list = new ArrayList<>();
        tasks_map = new HashMap<>();

         add_task( new TaskData(null, "2019-11-25", "2019-11-25",
                "Morai 24 , Jeru", "Tel-Aviv",
                "David", "34534", "driver 1", "order",
                "oreder note 1 ", "driver note 1"));

        add_task( new TaskData(null, "2019-11-25", "2019-11-25",
                "Morai 24 , Tel-Aviv", "Tel-Aviv",
                "David", "3123412", "driver 2", "order",
                "oreder note 2", "driver note 2"));

        add_task( new TaskData(null, "2019-11-25", "2019-11-25",
                "Morai 24 , Ariel", "Tel-Aviv",
                "David", "55555555", "driver 3", "order",
                "oreder note 3", "driver note 3"));


    }

    private static void add_task(TaskData task) {
        tasks_list.add(task);
        tasks_map.put(task.getTask_id(), task);
    }


}
