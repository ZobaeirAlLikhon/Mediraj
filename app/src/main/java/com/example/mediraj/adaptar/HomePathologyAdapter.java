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
import com.bumptech.glide.request.RequestOptions;
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
            holder.pathology_Cart_btn.setText("cart added");
            holder.pathology_Cart_btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.yellow));
        }else {
            holder.pathology_Cart_btn.setText("add to cart");
            holder.pathology_Cart_btn.setBackgroundTintList(context.getResources().getColorStateList(R.color.primaryColor));
        }

        holder.pathology_name.setText(allPathologyModels.get(position).getTitle());
        holder.pathology_price.setText(context.getString(R.string.moneySymbol)+" "+String.valueOf(allPathologyModels.get(position).getPrice()));
        Glide.with(context)
                .load(Constant.Pathology_AVATAR_URL +allPathologyModels.get(position).getLogo())
                .apply(new RequestOptions().placeholder(R.drawable.ic_pathology_1))
                .into(holder.circleImageView);
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

            //on click listener on addToCartBtn
            pathology_Cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allPathologyModels.get(getAdapterPosition()).isChecked()) {
                        allPathologyModels.get(getAdapterPosition()).setChecked(true);
                        pathologyClickInterface.sendOrDelete(getAdapterPosition(), "add");
                    } else {
                        allPathologyModels.get(getAdapterPosition()).setChecked(false);
                        pathologyClickInterface.sendOrDelete(getAdapterPosition(), "delete");

                    }
                    notifyDataSetChanged();
                }
            });


        }
    }
    public interface OnPathologyClickInterface{
        void sendOrDelete(int position,String opType);

    }
}
