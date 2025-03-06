package com.example.foodexpress_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodexpress_2.Domain.Slideritems;
import com.example.foodexpress_2.R;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.sliderViewholder> {
    private ArrayList<Slideritems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SliderAdapter(ArrayList<Slideritems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapter.sliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new sliderViewholder(LayoutInflater.from(context).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.sliderViewholder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if (position==sliderItems.size()-2){
            viewPager2.post(runnable);

        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class sliderViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public sliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);

        }
        void setImage(Slideritems slideritems){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop());
            Glide.with(context)
                    .load(slideritems.getUrl())
                    .apply(requestOptions)
                    .into(imageView);
        }

    }
}
