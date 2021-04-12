package com.example.mediraj.adaptar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;

import java.text.BreakIterator;
import java.util.List;
import java.util.zip.Inflater;

public class DocServiceAdapter extends RecyclerView.Adapter<DocServiceAdapter.ViewHolder> {
    Context context;
    List<String> text;
    List<Integer> image;

    //private Inflater inflater;

    public DocServiceAdapter(Context context, List<String> text, List<Integer> image) {
        this.context = context;
        this.text = text;
        this.image = image;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_service_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocServiceAdapter.ViewHolder holder, int position) {
        holder.text.setText(text.get(position));
        try {
            Glide.with(context).load(image.get(position)).into(holder.img);
        }catch (Exception exception){
            Log.e("adapter", exception.toString());
        }

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.doc_ServiceTV);
            img =itemView.findViewById(R.id.doc_ServiceIV);
        }
    }
}
