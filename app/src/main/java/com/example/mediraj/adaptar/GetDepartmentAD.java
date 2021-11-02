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
import com.example.mediraj.model.AllDepartmentModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetDepartmentAD extends RecyclerView.Adapter<GetDepartmentAD.MyViewHolder> {

    Context context;
    List<AllDepartmentModel.Datum> allDepartmentModel;
    DoctorInterface doctorInterface;

    public GetDepartmentAD(Context context, List<AllDepartmentModel.Datum> allDepartmentModel, DoctorInterface doctorInterface) {
        this.context = context;
        this.allDepartmentModel = allDepartmentModel;
        this.doctorInterface = doctorInterface;
    }

    @NonNull
    @NotNull
    @Override
    public GetDepartmentAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new GetDepartmentAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_department_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GetDepartmentAD.MyViewHolder holder, int position) {

        if (allDepartmentModel.get(position).getIsChecked()) {
            holder.card.setCardBackgroundColor(ContextCompat.getColor(context,R.color.primaryColor));
            holder.depart_name.setTextColor(Color.WHITE);
        } else {
            holder.card.setCardBackgroundColor(Color.WHITE);
            holder.depart_name.setTextColor(ContextCompat.getColor(context,R.color.battleship_grey));
        }

        holder.depart_name.setText(allDepartmentModel.get(position).title);

    }

    @Override
    public int getItemCount() {
        return allDepartmentModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView depart_name;
        CardView card;


        public MyViewHolder(View itemView) {
            super(itemView);
            depart_name = itemView.findViewById(R.id.depart_name);
            card = itemView.findViewById(R.id.deptCard);


            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0 ; i < allDepartmentModel.size() ; i++){
                        if (i==getAdapterPosition()){
                            if (!allDepartmentModel.get(i).getIsChecked()) {
                                doctorInterface.sendDeptId(allDepartmentModel.get(i).id);
                            }
                            allDepartmentModel.get(i).setIsChecked(true);
                        }else {
                            allDepartmentModel.get(i).setIsChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });


        }
    }

    public interface DoctorInterface{
        void sendDeptId(int deptId);
    }


}
