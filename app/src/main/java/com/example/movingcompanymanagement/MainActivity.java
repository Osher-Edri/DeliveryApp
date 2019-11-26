package com.example.movingcompanymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void driver(View view){
        startActivity(new Intent(getApplicationContext(), Driver.class));
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
        startActivity(new Intent(getApplicationContext(), workersTable.class));
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
    public void newTask(View view){
        startActivity(new Intent(getApplicationContext(), ActivityTransport.class));
    }
}