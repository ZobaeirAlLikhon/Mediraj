package com.example.mediraj.adaptar.history;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.example.mediraj.model.MedicineOrderModel;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    List<MedicineOrderModel.Datum> data;

    public HistoryAdapter(Context context, List<MedicineOrderModel.Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.med_history_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (data.get(position).prescription==null){
            holder.presImg.setVisibility(View.GONE);
        }else {
            holder.presImg.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(data.get(position).prescription)
                    .into(holder.presImg);
        }

        switch (data.get(position).serviceStatus) {
            case "Pending":
                holder.status.setText(data.get(position).serviceStatus);
                holder.status.setStrokeColorResource(R.color.colorAccent);
                break;
            case "Accepted":
                holder.status.setText(data.get(position).serviceStatus);
                holder.status.setStrokeColorResource(R.color.denim);
                break;
            case "Completed":
                holder.status.setText(data.get(position).serviceStatus);
                holder.status.setStrokeColorResource(R.color.primaryColor);
                break;
        }


        String dateString = context.getString(R.string.order_date)+" "+data.get(position).dates;
        holder.date.setText(dateString);

        if (data.get(position).medicines!=null){
            holder.medName.setVisibility(View.VISIBLE);
            String medString = context.getString(R.string.med_name)+" "+data.get(position).medicines;
            holder.medName.setText(medString);
        }else {
            holder.medName.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView presImg;
        TextView medName,date;
        MaterialButton status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            presImg = itemView.findViewById(R.id.presImg);
            medName = itemView.findViewById(R.id.medText);
            date = itemView.findViewById(R.id.orderDate);
            status = itemView.findViewById(R.id.status);
        }
    }
}
