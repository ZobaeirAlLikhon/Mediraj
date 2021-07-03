package com.example.mediraj.adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.example.mediraj.helper.Constant;
import com.example.mediraj.model.AllPathologyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePathologyAdapter extends RecyclerView.Adapter<HomePathologyAdapter.MyViewHoldr> {
    Context context;
    List<AllPathologyModel.Datum> allPathologyModels;
    OnPathologyClickInterface pathologyClickInterface;

    public HomePathologyAdapter(Context context, List<AllPathologyModel.Datum> allPathologyModels, OnPathologyClickInterface pathologyClickInterface) {
        this.context = context;
        this.allPathologyModels = allPathologyModels;
        this.pathologyClickInterface = pathologyClickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public HomePathologyAdapter.MyViewHoldr onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HomePathologyAdapter.MyViewHoldr(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_diagnostic_service_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomePathologyAdapter.MyViewHoldr holder, int position) {
        if (allPathologyModels.get(position).isChecked()){
            holder.cartAdded.setVisibility(View.VISIBLE);
            holder.pathology_Cart_btn.setVisibility(View.GONE);
        }else {
            holder.pathology_Cart_btn.setVisibility(View.VISIBLE);
            holder.cartAdded.setVisibility(View.GONE);
        }

        holder.pathology_name.setText(allPathologyModels.get(position).getTitle());
        holder.pathology_price.setText(String.valueOf(allPathologyModels.get(position).getPrice()));
        Glide.with(context).load(Constant.Pathology_AVATAR_URL +allPathologyModels.get(position).getLogo()).into(holder.circleImageView);
        //add to cart button on click
        holder.pathology_Cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!allPathologyModels.get(holder.getAdapterPosition()).isChecked())
               {
                   allPathologyModels.get(holder.getAdapterPosition()).setChecked(true);
                   pathologyClickInterface.sendOrDelete(holder.getAdapterPosition(),"add");
                   notifyDataSetChanged();

               }
            }
        });
        holder.cartAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allPathologyModels.get(holder.getAdapterPosition()).isChecked())
                {
                    allPathologyModels.get(holder.getAdapterPosition()).setChecked(false);
                    pathologyClickInterface.sendOrDelete(holder.getAdapterPosition(),"delete");
                    notifyDataSetChanged();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return allPathologyModels.size();
    }

    public class MyViewHoldr extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView pathology_name,pathology_price;
        Button pathology_Cart_btn;
        ImageView cartAdded;
        ImageView pathology_love;
        public MyViewHoldr(View itemview) {
            super(itemview);
            circleImageView=itemview.findViewById(R.id.cirIM_dia);
            pathology_name=itemview.findViewById(R.id.diagonstic_name);
            pathology_price=itemview.findViewById(R.id.diagonstic_price);
            pathology_Cart_btn=itemView.findViewById(R.id.addToCart_btn);
            cartAdded=itemview.findViewById(R.id.cartAdded);
            pathology_love=itemView.findViewById(R.id.diagonstic_love);

        }
    }
    public interface OnPathologyClickInterface{
        void sendOrDelete(int position,String opType);

    }
}
