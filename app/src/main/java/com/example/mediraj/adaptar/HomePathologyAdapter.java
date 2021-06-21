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

    public HomePathologyAdapter(Context context, List<AllPathologyModel.Datum> allPathologyModels) {
        this.context = context;
        this.allPathologyModels = allPathologyModels;
    }
    @NonNull
    @NotNull
    @Override
    public HomePathologyAdapter.MyViewHoldr onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HomePathologyAdapter.MyViewHoldr(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_home_pathology,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomePathologyAdapter.MyViewHoldr holder, int position) {
        holder.pathology_name.setText(allPathologyModels.get(position).getTitle());
        holder.pathology_price.setText(String.valueOf(allPathologyModels.get(position).getPrice()));
        Glide.with(context).load(Constant.Pathology_AVATAR_URL +allPathologyModels.get(position).getLogo())
                .apply(new RequestOptions().placeholder(R.drawable.ic_pathology_1))
                .fitCenter()
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
        ImageView pathology_love;
        public MyViewHoldr(View itemview) {
            super(itemview);
            circleImageView=itemview.findViewById(R.id.cirimg_pathology);
            pathology_name=itemview.findViewById(R.id.pathology_name);
            pathology_price=itemview.findViewById(R.id.pathology_price);
            pathology_Cart_btn=itemView.findViewById(R.id.pathology_Cart_btn);
            pathology_love=itemView.findViewById(R.id.pathology_love);

        }
    }
}
