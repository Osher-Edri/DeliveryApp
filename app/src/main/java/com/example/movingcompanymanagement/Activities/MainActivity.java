package com.example.movingcompanymanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.TaskTable;
import com.example.movingcompanymanagement.taskOverview;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void driver(View view){
        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        //finish();
    }
    public void manager(View view){
        startActivity(new Intent(getApplicationContext(), AddWorker.class));
        //finish();
    }

    public void taskOverview(View view){
        // test for load data
        startActivity(new Intent(getApplicationContext(), taskOverview.class));
    }

    public void taskTable(View view){
        startActivity(new Intent(getApplicationContext(), TaskTable.class));
    }



    public void workersTable(View view) {
        startActivity(new Intent(getApplicationContext(), WorkersTableActivity.class));
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void newTask(View view){
        startActivity(new Intent(getApplicationContext(), NewTaskActivity.class));
    }

    public void showTask(View view){
        startActivity(new Intent(getApplicationContext(), TaskDetailsActivity.class));
    }
}