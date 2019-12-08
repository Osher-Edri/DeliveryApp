package com.example.movingcompanymanagement.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class NewTaskActivity extends AppCompatActivity {

    private MaterialEditText mFullName, mDateOfTransport, mDestinationAddress, mOriginAddress, mPhoneNumber, mTransportDetails;

    private Button btn_submit;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        mFullName = (MaterialEditText)findViewById(R.id.transport_fullName);
        mDateOfTransport = (MaterialEditText)findViewById(R.id.transport_dateOfTransport);
        mDestinationAddress = (MaterialEditText)findViewById(R.id.transport_destinationAddress);
        mOriginAddress = (MaterialEditText)findViewById(R.id.transport_originAddress);
        mPhoneNumber = (MaterialEditText)findViewById(R.id.transport_phoneNumber);
        mTransportDetails = (MaterialEditText)findViewById(R.id.transport_transportDetails);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tasks");

        btn_submit = findViewById(R.id.btn_transport_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sFullName = mFullName.getText().toString();
                String sDateOfTransport = mDateOfTransport.getText().toString();
                String sDestinationAddress = mDestinationAddress.getText().toString();
                String sOriginAddress = mOriginAddress.getText().toString();
                String sPhoneNumber = mPhoneNumber.getText().toString();
                String sTransportDetails = mTransportDetails.getText().toString();

                TaskData newTask = new TaskData();

                newTask.setContact_name(sFullName);
                newTask.setOrder_date(sDateOfTransport);
                newTask.setAddress(sDestinationAddress);
                newTask.setContact_phone(sPhoneNumber);
                newTask.setOrder_note(sTransportDetails);
                newTask.setOriginAddress(sOriginAddress);

                databaseReference.push().setValue(newTask);

            }
        });
    }
}
