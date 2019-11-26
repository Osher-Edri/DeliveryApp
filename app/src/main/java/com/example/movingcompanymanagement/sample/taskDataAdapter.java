package com.example.movingcompanymanagement.sample;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.movingcompanymanagement.R;
import com.example.movingcompanymanagement.modal.TaskData;

import java.util.List;

public class taskDataAdapter extends RecyclerView.Adapter<taskDataAdapter.ViewHolder> {

    private List<TaskData> mItems;
    private Context mContext;

    public taskDataAdapter(Context context, List<TaskData> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public taskDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(taskDataAdapter.ViewHolder holder, int position) {
        TaskData item = mItems.get(position);

            holder.address.setText(item.getAddress());
            holder.order_date.setText(item.getOrder_date());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address;
        public TextView order_date;
        public ViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.address);
            order_date = (TextView) itemView.findViewById(R.id.order_date);

        }
    }
}
