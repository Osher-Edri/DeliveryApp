package com.example.movingcompanymanagement.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;

import java.util.List;

public class taskDataAdapterListView extends ArrayAdapter {

    List<TaskData> m_task_data;
    LayoutInflater mInflater;

    public taskDataAdapterListView(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.list_task ,  objects);

        m_task_data = objects;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_task, parent, false);
        }

        TextView address =(TextView) convertView.findViewById(R.id.address);
        TextView order_date =(TextView) convertView.findViewById(R.id.order_date);

        TaskData task = m_task_data.get(position);
        address.setText(task.getAddress());
        order_date.setText(task.getOrder_date());

        return convertView;
    }
}
