package com.example.movingcompanymanagement.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

public class TaskDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    MyAdapter adapter;
    List<TaskData> taskData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        taskData = new ArrayList<>();
        adapter = new MyAdapter(taskData);
        firebaseDatabase = FirebaseDatabase.getInstance();

        getDataFirebase();

    }

    void getDataFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TaskData data = dataSnapshot.getValue(TaskData.class);

                taskData.add(data);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_fieald, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TaskData data = taskData.get(position);

            //holder.fullName.setText(data.getContact_name());
            holder.order_date.setText(data.getOrder_date());
            //holder.address.setText(data.getAddress());
            // holder.contact_phone.setText(data.getContact_phone());
            // holder.order_note.setText(data.getOrder_note());
            holder.area.setText(data.getArea());

//            String[] drivers = new String[]{"driver1","driver2","driver3"};
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(TaskDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, drivers);
//            holder.driver.setAdapter(adapter);
//

        }

        @Override
        public int getItemCount() {
            return taskData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView fullName, order_date, address, contact_phone, order_note, originAddress, area;
            Spinner driver;
            ArrayList<String> drivers = new ArrayList<>();

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                //fullName = (TextView)itemView.findViewById(R.id.view_fullName);
                order_date = (TextView) itemView.findViewById(R.id.view_dateTransport);
                //address = (TextView)itemView.findViewById(R.id.view_destinationAddress);
                //contact_phone = (TextView)itemView.findViewById(R.id.view_phoneNumber);
                //order_note = (TextView)itemView.findViewById(R.id.view_transportDetails);
                area = (TextView) itemView.findViewById(R.id.view_area);
                driver = (Spinner) itemView.findViewById(R.id.view_selectDriver);

                // todo - filter only drivers + put it in function or mybe object
                databaseReference = FirebaseDatabase.getInstance().getReference("User");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        drivers.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            String name = ds.child("firstName").getValue(String.class);
                            Log.i("noam", name);
                            drivers.add(name);

                        }
                        Log.i("noam: ", drivers.toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                ArrayAdapter<String> adapter = new ArrayAdapter<>(TaskDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, drivers);
                driver.setAdapter(adapter);


                driver.setSelection(0,false);

                driver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                    {
                        @Override
                        public void onItemSelected (AdapterView < ? > parent, View view,
                        int position, long id){
                        String selected_d = parent.getItemAtPosition(position).toString();
                        TaskData data = taskData.get(position);
                        data.setDriver(selected_d);
                        Toast.makeText(TaskDetailsActivity.this, "driver selected: " + selected_d, Toast.LENGTH_SHORT).show();
                        databaseReference.push().setValue(data);
                    }

                        @Override
                        public void onNothingSelected (AdapterView < ? > parent){

                    }
                    });


                }
            }
        }


    }
