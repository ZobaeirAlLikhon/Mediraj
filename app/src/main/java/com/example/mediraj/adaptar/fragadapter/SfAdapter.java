package com.example.mediraj.adaptar.fragadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediraj.R;
import com.example.mediraj.localdb.AppDatabase;
import com.example.mediraj.localdb.PathologyServices;
import com.example.mediraj.localdb.SurgicalService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SfAdapter extends RecyclerView.Adapter<SfAdapter.ViewHolder> {
    Context context;
    List<SurgicalService> dataList;
    SfItemClick sfItemClick;
    AppDatabase db;
    int productQuantity;

    public SfAdapter(Context context, List<SurgicalService> dataList, SfItemClick sfItemClick) {
        this.context = context;
        this.dataList = dataList;
        this.sfItemClick = sfItemClick;
    }

    @NonNull
    @NotNull
    @Override
    public SfAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cart_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SfAdapter.ViewHolder holder, int position) {
        db = AppDatabase.getDbInstance(context);
        holder.title.setText(dataList.get(position).getItem_title());
        holder.detail.setText(dataList.get(position).getItem_qty() + " x " + context.getString(R.string.moneySymbol) + " " + dataList.get(position).getItem_price());
        holder.price.setText("Total: " + context.getString(R.string.moneySymbol) + " " + dataList.get(position).getItem_subtotal());
        holder.quantity.setText(dataList.get(position).getItem_qty() + "");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, detail, price, quantity;
        ImageView plus, minus,delete;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.pTitle);
            detail = itemView.findViewById(R.id.pDetails);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.start_quantity);
            plus = itemView.findViewById(R.id.btnplus);
            minus = itemView.findViewById(R.id.btnminus);
            delete = itemView.findViewById(R.id.deleteItem);
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productQuantity = Integer.parseInt(dataList.get(getAdapterPosition()).getItem_qty());
                    if (productQuantity == 1) {
                        dataList.get(getAdapterPosition()).setItem_qty(String.valueOf(productQuantity));
                        dataList.get(getAdapterPosition()).setItem_subtotal(productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                        db.pathologyServicesDao().updateUserPath(dataList.get(getAdapterPosition()).getId(),productQuantity,productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                    } else {
                        productQuantity -= 1;
                        dataList.get(getAdapterPosition()).setItem_qty(String.valueOf(productQuantity));
                        dataList.get(getAdapterPosition()).setItem_subtotal(productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                        db.pathologyServicesDao().updateUserPath(dataList.get(getAdapterPosition()).getId(),productQuantity,productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                    }
                    notifyDataSetChanged();
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productQuantity = Integer.parseInt(dataList.get(getAdapterPosition()).getItem_qty());
                    productQuantity += 1;
                    dataList.get(getAdapterPosition()).setItem_qty(String.valueOf(productQuantity));
                    dataList.get(getAdapterPosition()).setItem_subtotal(productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                    db.pathologyServicesDao().updateUserPath(dataList.get(getAdapterPosition()).getId(),productQuantity,productQuantity * dataList.get(getAdapterPosition()).getItem_price());
                    notifyDataSetChanged();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.surgicalServiceDao().deleteById_Surgical(dataList.get(getAdapterPosition()).getId());
                    int pos = getAdapterPosition();
                    dataList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos,dataList.size());
                    sfItemClick.senDataSize(dataList.size());
                }
            });
        }
    }

    public interface SfItemClick{
        void senDataSize(int size);
    }
}
