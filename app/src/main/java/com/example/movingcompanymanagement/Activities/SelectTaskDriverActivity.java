package com.example.movingcompanymanagement.Activities;

import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;


import java.util.ArrayList;
import java.util.List;

public class SelectTaskDriverActivity extends BaseActivity {

    private RecyclerView recyclerView;
    MyAdapter adapter;
    List<TaskData> tasks;
    DatabaseReference databaseReference;
    DatabaseReference getTaskReference;
    FirebaseAuth firebaseAuth;
    ArrayList<String> driverNames = new ArrayList<>();
    ArrayList<UserData> drivers = new ArrayList<>();
    ArrayList<DriverNameAndIDHolder> nameAndIDList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_select_task_driver);
        recyclerView = findViewById(R.id.manager_task_list_recycler);
        recyclerView.setHasFixedSize(true);
        firebaseAuth = FirebaseAuth.getInstance();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        tasks = new ArrayList<>();
        adapter = new MyAdapter(tasks);
        nameAndIDList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                driverNames.clear();
//                drivers.clear();
                driverNames.add("------");
                nameAndIDList.clear();
                //when wanting to set driver to "null"
                DriverNameAndIDHolder nullChoice= new DriverNameAndIDHolder("------", "------");
                nameAndIDList.add(nullChoice);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String role = ds.child("role").getValue(String.class);
                    if (role.equals("Driver")) {
                        String name = ds.child("firstName").getValue(String.class);
                        String ID = ds.child("id").getValue(String.class);
                        DriverNameAndIDHolder nextDriver = new DriverNameAndIDHolder(name, ID);
                        nameAndIDList.add(nextDriver);
//                        driverNames.add(name);
//                        drivers.add(tempDriver);
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
                tasks.clear();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    TaskData data = ds.getValue(TaskData.class);
                    if(data.getSubmit_by_user().equals(firebaseAuth.getUid()))
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
            String currentTaskDriverID = data.getDriver();
            Log.i("Test driverID", currentTaskDriverID);
            for(int index = 0; index < nameAndIDList.size(); index++){
                DriverNameAndIDHolder temp = nameAndIDList.get(index);
                if(temp.driverID.equals(currentTaskDriverID)) {
                    holder.driver_spinner.setSelection(index, false);
                    break;
                }
            }
           // int driverNameIndex = holder.driverNamesAndIDs.getPosition(data.getDriver());

        }

        @Override
        public int getItemCount() {
            return taskData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView order_date, area, edtFullName,edtPhoneNumber, edtOriginAddress, edtDestinationAddress, edtTransportDay, edtTransportDescription;
            Spinner driver_spinner =  itemView.findViewById(R.id.manager_spinner_select_driver);
            String taskKey;
            TaskData taskData;
            ArrayAdapter<DriverNameAndIDHolder> driverNamesAndIDs;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                order_date = itemView.findViewById(R.id.manager_date);
                area = itemView.findViewById(R.id.manager_area);
                driverNamesAndIDs = new ArrayAdapter<>(SelectTaskDriverActivity.this, android.R.layout.simple_spinner_dropdown_item, nameAndIDList);
                driver_spinner.setAdapter(driverNamesAndIDs);
                driver_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        DriverNameAndIDHolder selectedDriver =  (DriverNameAndIDHolder)parent.getItemAtPosition(position);
//                        String selectedDriverName = ((DriverNameAndIDHolder)parent.getItemAtPosition(position)).driverName;
//                        String selectedDriverID = ((DriverNameAndIDHolder)parent.getItemAtPosition(position)).driverID;
                        String selectedDriverID = selectedDriver.driverID;
                        String selectedDriverName = selectedDriver.driverName;
                        Log.i("TestDriverIDOnSelected", selectedDriverID);
                        String currentDriverID = taskData.getDriver();
                        //UserData selectedDriver = drivers.get(position - 1);
                        getTaskReference = FirebaseDatabase.getInstance().getReference("Tasks").child(taskKey);
                        if(!selectedDriverName.equals("------") && !currentDriverID.equals(selectedDriverID)) {
                            Log.i("Test task object", taskData.toString());
                            driver_spinner.setSelection(position);
                            taskData.setDriver(selectedDriverID);
                            Toast.makeText(SelectTaskDriverActivity.this, "driver selected: " + selectedDriverName, Toast.LENGTH_SHORT).show();
                            getTaskReference.setValue(taskData);
                        }
                        else if(selectedDriverName.equals("------") && !selectedDriverName.equals(taskData.getDriver())){
                            taskData.setDriver("------");
                            getTaskReference.setValue(taskData);
                            Toast.makeText(SelectTaskDriverActivity.this, "Driver selection removed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                
                order_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View register_layout = LayoutInflater.from(SelectTaskDriverActivity.this)
                                .inflate(R.layout.task_order_details, null);

                        edtFullName = (TextView)register_layout.findViewById(R.id.edt_fullName);
                        edtPhoneNumber = (TextView)register_layout.findViewById(R.id.edt_phone_number);
                        edtOriginAddress = (TextView)register_layout.findViewById(R.id.edt_origin_address);
                        edtDestinationAddress = (TextView)register_layout.findViewById(R.id.edt_destination_address);
                        edtTransportDay = (TextView)register_layout.findViewById(R.id.edt_transport_day);
                        edtTransportDescription = (TextView)register_layout.findViewById(R.id.edt_transport_description);

                        new MaterialStyledDialog.Builder(SelectTaskDriverActivity.this)
                                .setCustomView(register_layout)
                                .withDarkerOverlay(true)
                                .show();

                        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks").child(taskKey);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String contactFullName = dataSnapshot.child("contact_name").getValue(String.class);
                                String contactPhoneNumber = dataSnapshot.child("contact_phone").getValue(String.class);
                                String contactOriginAddress = dataSnapshot.child("originAddress").getValue(String.class);
                                String contactDestinationAddress = dataSnapshot.child("address").getValue(String.class);
                                String contactTransportDay = dataSnapshot.child("order_date").getValue(String.class);
                                String contactTransportDescription = dataSnapshot.child("order_note").getValue(String.class);

                                edtFullName.setText(contactFullName);
                                edtPhoneNumber.setText(contactPhoneNumber);
                                edtOriginAddress.setText(contactOriginAddress);
                                edtDestinationAddress.setText(contactDestinationAddress);
                                edtTransportDay.setText(contactTransportDay);
                                edtTransportDescription.setText(contactTransportDescription);

                            }
                            

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        }
    }
    public class DriverNameAndIDHolder{
        String driverName;
        String driverID;
        DriverNameAndIDHolder(String driverName, String driverID){
            this.driverID = driverID;
            this.driverName = driverName;
        }

        @Override
        public String toString(){
            return this.driverName;
        }
    }
}
