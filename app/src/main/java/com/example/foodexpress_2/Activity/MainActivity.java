package com.example.foodexpress_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.foodexpress_2.AboutusActivity;
import com.example.foodexpress_2.Adapter.CategoryAdapter;
import com.example.foodexpress_2.Adapter.PopularAdapter;
import com.example.foodexpress_2.Adapter.SliderAdapter;
import com.example.foodexpress_2.Domain.CategoryDomain;
import com.example.foodexpress_2.Domain.ItemsDomain;
import com.example.foodexpress_2.Domain.Slideritems;
import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    EditText search_box;


    ImageView imageprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black, this.getTheme()));


        initbanner();
        initcategory();
        initpopular();
        bottomNavigation();

    }

    private void bottomNavigation() {
        binding.cartbtn.setOnClickListener((View v) -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        binding.profilebtn.setOnClickListener((View v) ->{
            startActivity(new Intent(MainActivity.this, AboutusActivity.class));
        });

        binding.homebtn.setOnClickListener((View v) ->{
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        });
    }

    private void initpopular() {
        DatabaseReference myRef = database.getReference("Items");
        binding.progressBarpopluar.setVisibility(View.VISIBLE);
        ArrayList<ItemsDomain> items = new ArrayList<>();
        Query query = myRef.orderByChild("popfood").equalTo(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(ItemsDomain.class));
                    }
                    if (items.size()>0){
                        binding.recylerPop.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                        binding.recylerPop.setAdapter(new PopularAdapter(items));
                    }
                    binding.progressBarpopluar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initcategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBar2.setVisibility(View.VISIBLE);
        ArrayList<CategoryDomain> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(CategoryDomain.class));
                    }
                    if (!items.isEmpty()){
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                                LinearLayoutManager.HORIZONTAL,false));
                        binding.recyclerView.setAdapter(new CategoryAdapter(items));
                    }
                    binding.progressBar2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initbanner() {
        DatabaseReference myRef = database.getReference("Banner");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Slideritems> items=new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Slideritems.class));
                    }
                    banner(items);
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void banner(ArrayList<Slideritems> items) {
        binding.viewpagerslider.setAdapter(new SliderAdapter(items,binding.viewpagerslider));
        binding.viewpagerslider.setClipToPadding(false);
        binding.viewpagerslider.setClipChildren(false);
        binding.viewpagerslider.setOffscreenPageLimit(3);
        binding.viewpagerslider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewpagerslider.setPageTransformer(compositePageTransformer);
    }
}