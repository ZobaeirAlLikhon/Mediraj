package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.model.AllDepartmentModel;
import com.example.mediraj.model.AllPathologyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetDepartmentAD extends RecyclerView.Adapter<GetDepartmentAD.MyViewHolder> {

    Context context;
    List<AllDepartmentModel.Datum> allDepartmentModel;

    public GetDepartmentAD(Context context, List<AllDepartmentModel.Datum> allDepartmentModel) {
        this.context = context;
        this.allDepartmentModel=allDepartmentModel;
    }

    @NonNull
    @NotNull
    @Override
    public GetDepartmentAD.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new GetDepartmentAD.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_department_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GetDepartmentAD.MyViewHolder holder, int position) {
        holder.depart_name.setText(allDepartmentModel.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return allDepartmentModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView depart_name;
        public MyViewHolder( View itemView) {
            super(itemView);
            depart_name = itemView.findViewById(R.id.depart_name);
        }
    }


}
