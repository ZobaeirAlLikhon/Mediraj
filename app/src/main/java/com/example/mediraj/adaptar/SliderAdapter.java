package com.example.mediraj.adaptar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mediraj.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder> {

    String [] image;
    Context context;

    public SliderAdapter(String[] image, Context context) {
        this.image = image;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.smart_image_slider,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        try {
            Log.e("image url",image[position].toString());
            Glide.with(context).load(image[position]).fitCenter()
                    .into(viewHolder.imageView);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getCount() {
        return image.length;
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImage);
        }
    }
}
