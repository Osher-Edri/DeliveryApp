package com.example.movingcompanymanagement.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectTaskDriverActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MyAdapter adapter;
    List<TaskData> tasks;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference getTaskReference;
    ArrayList<String> drivers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_select_task_driver);
        recyclerView = findViewById(R.id.manager_task_list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        tasks = new ArrayList<>();
        adapter = new MyAdapter(tasks);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drivers.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String role = ds.child("role").getValue(String.class);
                    if (role.equals("Driver")) {
                        String name = ds.child("firstName").getValue(String.class);
                        drivers.add(name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        getDataFirebase();
    }

    void getDataFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    TaskData data = ds.getValue(TaskData.class);
                    tasks.add(data);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        List<TaskData> taskData;

        public MyAdapter(List<TaskData> taskData) {
            this.taskData = taskData;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_task_list_view_holder, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TaskData data = taskData.get(position);
            holder.taskKey = data.getTask_id();
            holder.taskData = data;
            holder.order_date.setText(data.getOrder_date());
            holder.area.setText(data.getArea());
        }

        @Override
        public int getItemCount() {
            return taskData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView order_date, area;
            Spinner driver;
            String taskKey;
            TaskData taskData;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                order_date = itemView.findViewById(R.id.manager_date);
                area = itemView.findViewById(R.id.manager_area);
                driver = itemView.findViewById(R.id.manager_spinner_select_driver);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectTaskDriverActivity.this, android.R.layout.simple_spinner_dropdown_item, drivers);
                driver.setAdapter(adapter);
                driver.setSelection(0, false);

                driver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getTaskReference = FirebaseDatabase.getInstance().getReference("Tasks").child(taskKey);
                        String selected_d = parent.getItemAtPosition(position).toString();
                        taskData.setDriver(selected_d);
                        Toast.makeText(SelectTaskDriverActivity.this, "driver selected: " + selected_d, Toast.LENGTH_SHORT).show();
                        getTaskReference.setValue(taskData);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        }
    }


}
