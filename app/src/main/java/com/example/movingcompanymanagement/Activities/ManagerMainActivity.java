package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerMainActivity extends MangerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        Intent intent = getIntent();
        mangerUser = (UserData) intent.getSerializableExtra("current user");

    }

    public void selectTaskDriver(View view){
        Intent i = new Intent(getApplicationContext(), SelectTaskDriverActivity.class);
        i.putExtra("current user", mangerUser);
        i.putExtra("filter", "open");
        startActivity(i);
    }
    // todo - filter failed tasks
    public void showFailedTasks(View view) {
        Intent i = new Intent(getApplicationContext(), SelectTaskDriverActivity.class);
        i.putExtra("current user", mangerUser);
        i.putExtra("filter", "problem");
        startActivity(i);
    }

    // todo - filter complete tasks
    public void showCompletedTasks(View view) {
        Intent i = new Intent(getApplicationContext(), SelectTaskDriverActivity.class);
        i.putExtra("current user", mangerUser);
        i.putExtra("filter", "close");
        startActivity(i);
    }
}
