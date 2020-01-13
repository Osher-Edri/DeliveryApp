package com.example.movingcompanymanagement.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.UserData;
import com.google.firebase.auth.FirebaseAuth;

public class MangerBaseActivity extends AppCompatActivity {

    UserData mangerUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_menu, menu);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                Intent i_task = new Intent(getApplicationContext(), NewTaskActivity.class);
                i_task.putExtra("current user", mangerUser);
                startActivity(i_task);
                return true;
            case R.id.new_worker:
                Intent i_worker = new Intent(getApplicationContext(), AddWorkerActivity.class);
                i_worker.putExtra("current user", mangerUser);
                startActivity(i_worker);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            case android.R.id.home: // back arrow prresed
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
