package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;

import java.util.List;

public class DoctorAdpter extends RecyclerView.Adapter<DoctorAdpter.ViewHolder> {
    Context context;
    List<String> titles;
    List<Integer> images;

    public DoctorAdpter(Context context, List<String> titles, List<Integer> images) {
        this.context = context;
        this.titles = titles;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_doctors,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titels.setText(titles.get(position));
        holder.docIV.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView docIV;
        TextView titels;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docIV = itemView.findViewById(R.id.docIV);
            titels = itemView.findViewById(R.id.doc_Name);
            titels = itemView.findViewById(R.id.doc_Designation);
            titels = itemView.findViewById(R.id.hospital_Name);
        }
    }
}
