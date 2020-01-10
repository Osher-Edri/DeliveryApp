package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.AreaComparator;
import com.example.movingcompanymanagement.modal.DateComparator;
import com.example.movingcompanymanagement.modal.TaskChainedComparator;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverTasksListActivity extends DriverBaseActivity {
    UserData driverData;
    private RecyclerView recyclerView;
    List<TaskData> tasks;
    DatabaseReference tasksDatabaseReference;
    FirebaseDatabase  firebaseDatabase;
    FirebaseAuth firebaseAuth;
    MyAdapter adapter;
    String filter_;
    LocalDateTime now;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_driver_task_list);
        //remov title bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        driverData = (UserData) intent.getSerializableExtra("current user");
        filter_ = (String) intent.getSerializableExtra("filter");
        now = LocalDateTime.now();
        recyclerView = findViewById(R.id.driver_task_list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        tasks = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getDataFirebase();
        adapter = new MyAdapter(tasks);
    }

    private void getDataFirebase() {
        tasksDatabaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        tasksDatabaseReference.addValueEventListener(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            tasks.clear();

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                TaskData task = ds.getValue(TaskData.class);

                if(driverData.getId().equals(task.getDriver())) {
                    if (filter_.equals("today")) {
                            int day = now.getDayOfMonth();
                            int month = now.getMonthValue();
                            int year = now.getYear();
                            if(task.getTaskMonth() == month && task.getTaskYear() == year && task.getTaskDay() == day)
                                tasks.add(task);
                    }
                    else{
                        tasks.add(task);
                    }
                }
            }
            TaskChainedComparator chainComparator = new TaskChainedComparator(new DateComparator(), new AreaComparator());
            Collections.sort(tasks, chainComparator );
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        List<TaskData> taskData;

        public MyAdapter(List<TaskData> taskData) {
            this.taskData = taskData;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_task_list_view_holder, parent, false);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TaskData data = taskData.get(position);
            holder.taskKey = data.getTask_id();
            holder.taskData = data;
            holder.task_date.setText(data.getTask_date());
            holder.area.setText(data.getArea());
        }

        @Override
        public int getItemCount() {
            return taskData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView task_date, area;
            String taskKey;
            TaskData taskData;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                task_date =  itemView.findViewById(R.id.driver_date);
                area =  itemView.findViewById(R.id.driver_area);

                // open the task details by click
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TaskDetailsActivityManger.class);
                        intent.putExtra("current data", taskData);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
