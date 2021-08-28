package com.example.mediraj.adaptar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.model.DateModel;

import java.util.List;

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.ViewHolder> {

    Context context;
    List<DateModel> dateModels;
    OnCalDataClick onCalDataClick;

    public CalAdapter(Context context, List<DateModel> dateModels, OnCalDataClick onCalDataClick) {
        this.context = context;
        this.dateModels = dateModels;
        this.onCalDataClick = onCalDataClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.date_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (dateModels.get(position).isChecked()){
            holder.card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.primaryColor));
            holder.dayString.setTextColor(Color.WHITE);
            holder.dayNumber.setTextColor(Color.WHITE);
        }else {
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.dayString.setTextColor(ContextCompat.getColor(context,R.color.battleship_grey));
            holder.dayNumber.setTextColor(ContextCompat.getColor(context,R.color.battleship_grey));
        }


        holder.dayString.setText(dateModels.get(position).getDayName());
        holder.dayNumber.setText(dateModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return dateModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayString,dayNumber;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayString = itemView.findViewById(R.id.dayText);
            dayNumber = itemView.findViewById(R.id.dateText);
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0 ; i < dateModels.size() ; i++){
                        if (i==getAdapterPosition()){
                            if (dateModels.get(i).isChecked()){
                                onCalDataClick.sendData(0,0,0);
                                dateModels.get(i).setChecked(false);
                            }else {
                                onCalDataClick.sendData(Integer.parseInt(dateModels.get(i).getDate()),Integer.parseInt(dateModels.get(i).getMonth()),Integer.parseInt(dateModels.get(i).getYear()));
                                dateModels.get(i).setChecked(true);
                            }
                        }else {
                            dateModels.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface OnCalDataClick{
        void sendData(int day,int month,int year);
    }
}
