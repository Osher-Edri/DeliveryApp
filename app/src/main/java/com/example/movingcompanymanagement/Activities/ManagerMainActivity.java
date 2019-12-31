package com.example.movingcompanymanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class ManagerMainActivity extends BaseActivity {
    UserData mangerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        Intent intent = getIntent();
        mangerUser = (UserData) intent.getSerializableExtra("current user");
       // Log.i("noam", mangerUser.getFirstName());

    }
    public void addWorker(View view){
        startActivity(new Intent(getApplicationContext(), AddWorkerActivity.class));
    }

    public void newTask(View view){
        Intent i = new Intent(getApplicationContext(), NewTaskActivity.class);
        i.putExtra("current user", mangerUser);
        startActivity(i);
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
