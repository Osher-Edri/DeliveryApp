package com.example.movingcompanymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class TaskTable extends AppCompatActivity {

    private  boolean table_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_table);
    }

    public void collapseTable(View view) {
        TableLayout table = findViewById(R.id.tasks_table);
        Button switch_btn = (Button) findViewById(R.id.switch_show);


        table.setColumnCollapsed(0,table_flg);
        table.setColumnCollapsed(2,table_flg);

        if (table_flg)  {
            table_flg = false;
            switch_btn.setText(R.string.show);
        } else {
            table_flg = true;
            switch_btn.setText(R.string.hide);

        }



    }
}
