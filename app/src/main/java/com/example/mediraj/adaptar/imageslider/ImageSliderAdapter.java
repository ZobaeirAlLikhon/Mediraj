package com.example.mediraj.adaptar.imageslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageAdapterVH> {
    private Context context;
    private String [] img;

    public ImageSliderAdapter(Context context, String[] img) {
        this.context = context;
        this.img = img;
    }

    @Override
    public ImageAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent,false);
        return new ImageAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(ImageAdapterVH viewHolder, int position) {
        Glide.with(viewHolder.itemView)
                .load(img[position])
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    public static class ImageAdapterVH extends SliderViewAdapter.ViewHolder {
        ImageView imageViewBackground;
        public ImageAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}
