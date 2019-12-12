package com.example.movingcompanymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movingcompanymanagement.modal.TaskData;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends ArrayAdapter<TaskData> {

    private Context mContext;
    int mResource ;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull List<TaskData> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
          String address = getItem(position).getAddress();
          String date = getItem(position).getTask_date();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView =inflater.inflate(mResource, parent, false);

        TextView list_address = (TextView) convertView.findViewById(R.id.address);
        TextView list_date = (TextView) convertView.findViewById(R.id.order_date);

        list_address.setText(address);
        list_date.setText(date);

        return convertView;

    }
}
