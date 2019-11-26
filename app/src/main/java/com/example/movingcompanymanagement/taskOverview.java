package com.example.movingcompanymanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.sample.SampleDataProvider;
import com.example.movingcompanymanagement.sample.taskDataAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class taskOverview extends AppCompatActivity {

//    TextView task_out;

    List<TaskData>  tasks_list = SampleDataProvider.tasks_list;
    List<String> task_address = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

        Collections.sort(tasks_list, new Comparator<TaskData>() {
            @Override
            public int compare(TaskData o1, TaskData o2) {
                return o1.getAddress().compareTo(o2.getAddress());
            }
        });

        taskDataAdapter adapter = new taskDataAdapter(this, tasks_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_tasks);
        recyclerView.setAdapter(adapter);

    }
}
