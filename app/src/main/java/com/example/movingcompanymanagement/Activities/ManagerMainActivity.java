package com.example.movingcompanymanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movingcompanymanagement.R;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
    }
    public void addWorker(View view){
        startActivity(new Intent(getApplicationContext(), AddWorkerActivity.class));
    }

    public void newTask(View view){
        startActivity(new Intent(getApplicationContext(), NewTaskActivity.class));
    }
    public void selectTaskDriver(View view){
        startActivity(new Intent(getApplicationContext(), SelectTaskDriverActivity.class));
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
