package com.example.movingcompanymanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.example.movingcompanymanagement.modal.UserData;
import com.example.movingcompanymanagement.sample.SampleDataProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    List<TaskData> tasks = SampleDataProvider.tasks_list;
    DatabaseReference databaseReference;
    UserData user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        firebaseDatabase = FirebaseDatabase.getInstance();
        getDataFirebase();
        //firebaseDatabase.ge
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
                    String id = keyNode.child("firstname").getValue(String.class);
                   // if(task.getDriver())
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
