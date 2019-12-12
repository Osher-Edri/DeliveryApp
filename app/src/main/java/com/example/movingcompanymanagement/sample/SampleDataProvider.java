package com.example.movingcompanymanagement.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movingcompanymanagement.modal.TaskData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<TaskData> tasks_list;
    public static Map<String, TaskData> tasks_map;

    public List<TaskData> tasks;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

//    public  SampleDataProvider() {
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        getDataFirebase();
//    }


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

//    private void getDataFirebase() {
//        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                TaskData data = dataSnapshot.getValue(TaskData.class);
//                tasks.add(data);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                tasks.clear();
////                List<String> keys = new ArrayList<>();
////                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
////                    keys.add(keyNode.getKey());
////                    TaskData task = keyNode.getValue(TaskData.class);
////                    tasks.add(task);
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//
//    }


}
