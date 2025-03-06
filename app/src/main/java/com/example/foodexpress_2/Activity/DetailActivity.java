package com.example.foodexpress_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpress_2.Adapter.SliderAdapter;
import com.example.foodexpress_2.Domain.ItemsDomain;
import com.example.foodexpress_2.Domain.Slideritems;
import com.example.foodexpress_2.Helper.ManagmentCart;
import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity{
    ActivityDetailBinding binding;
    private ItemsDomain object;
    private int numberOrder=1;
    private ManagmentCart managmentCart;
    private int num=0;
    private Handler slideHandle = new Handler();
    ImageView bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

        getBundles();
        banners();
        setVariable();
    }
    private void setVariable() {
        managmentCart = new ManagmentCart(this);

       binding.back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });



        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = num + 1;
                binding.quantityNum.setText(num+"");
                binding.priceTxt.setText(("₹"+num * object.getPrice())+"");
            }
        });

        binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 0) {
                    num = num - 1;
                    binding.quantityNum.setText(num + "");
                    binding.priceTxt.setText("₹"+(num * object.getPrice()));
                }
            }
        });
    }

    private void banners() {
        ArrayList<Slideritems> slideritems = new ArrayList<>();
        for (int i = 0; i < object.getpicUrl().size(); i++){
            slideritems.add(new Slideritems(object.getpicUrl().get(i)));
        }
        binding.viewPageslider.setAdapter(new SliderAdapter(slideritems,binding.viewPageslider));
        binding.viewPageslider.setClipToPadding(false);
        binding.viewPageslider.setClipChildren(false);
        binding.viewPageslider.setOffscreenPageLimit(3);
        binding.viewPageslider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        object = (ItemsDomain) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.DDescriptionTxt.setText(object.getDescription());
        binding.priceTxt.setText("₹"+object.getPrice());
        binding.ratingBar.setRating((float) object.getRating());
        binding.ratingTxt.setText(object.getRating()+"");

        binding.addtocartbtn.setOnClickListener(v -> {
            object.setNumberinCart(numberOrder);
            managmentCart.insertFood(object);
        });
        binding.back.setOnClickListener(v -> finish());
    }
}