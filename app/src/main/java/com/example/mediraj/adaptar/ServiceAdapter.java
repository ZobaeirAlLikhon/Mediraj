package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    Context context;
    List<String> serviceName;
    List<Integer> images;
    ServiceInterface serviceInterface;

    public ServiceAdapter(Context context, List<String> serviceName, List<Integer> images, ServiceInterface serviceInterface) {
        this.context = context;
        this.serviceName = serviceName;
        this.images = images;
        this.serviceInterface = serviceInterface;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder (LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.titles.setText(serviceName.get(position));
        Glide.with(context).load(images.get(position)).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return serviceName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titles;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titles = itemView.findViewById(R.id.serviceName);
            icon = itemView.findViewById(R.id.serviceImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceInterface.onClickInterface(serviceName.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ServiceInterface{
        void onClickInterface(String serName);
    }
}
