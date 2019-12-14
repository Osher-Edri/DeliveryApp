package com.example.movingcompanymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.sample.SampleDataProvider;
import com.example.movingcompanymanagement.sample.taskDataAdapter;
import com.example.movingcompanymanagement.sample.taskDataAdapterListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class taskOverview extends AppCompatActivity {

//    TextView task_out;

//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
//    SampleDataProvider data = new SampleDataProvider();

//    List<TaskData>  tasks;
    List<TaskData>  tasks = SampleDataProvider.tasks_list;
    List<String> adrresses = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
//    ListView mListView = (ListView) findViewById(R.id.tasks_list);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        getDataFirebase();




//        Collections.sort(tasks_list, new Comparator<TaskData>() {
//            @Override
//            public int compare(TaskData o1, TaskData o2) {
//                return o1.getAddress().compareTo(o2.getAddress());
//            }
//        });




        taskDataAdapterListView adapter = new taskDataAdapterListView(this, tasks );
        ListView listView = (ListView) findViewById(R.id.tasks_list);
        listView.setAdapter(adapter);

    }

    private void getDataFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    TaskData task = keyNode.getValue(TaskData.class);
                    tasks.add(task);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//    }
//


    }




}
