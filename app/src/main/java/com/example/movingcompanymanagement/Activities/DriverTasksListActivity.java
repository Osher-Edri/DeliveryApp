package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DriverTasksListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<TaskData> tasks;
    DatabaseReference tasksDatabaseReference;
    FirebaseDatabase  firebaseDatabase;
    DatabaseReference driverNameDatabaseReference;
    FirebaseAuth firebaseAuth;
    String driverName;
    ValueEventListener nameEventListener;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_task_list);
        recyclerView = findViewById(R.id.driver_task_list_recycler);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        tasks = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setNameListener();
        driverNameDatabaseReference = FirebaseDatabase.getInstance().getReference("User");
        driverNameDatabaseReference.addValueEventListener(nameEventListener);
        getDataFirebase();
        adapter = new MyAdapter(tasks);




    }

    private void getDataFirebase() {
        tasksDatabaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        tasksDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("Test task", ds.getValue(TaskData.class).toString());
                    TaskData task = ds.getValue(TaskData.class);
                    tasks.add(task);
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    private void setNameListener() {
        nameEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driverName = dataSnapshot.child(firebaseAuth.getUid()).child("firstName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
    }

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
            holder.date.setText(data.getOrder_date());
            holder.area.setText(data.getArea());
        }

        @Override
        public int getItemCount() {
            return taskData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView date, area;
            String taskKey;
            TaskData taskData;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                date =  itemView.findViewById(R.id.driver_date);
                area =  itemView.findViewById(R.id.driver_area);
            }
        }
    }
}