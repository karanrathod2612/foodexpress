package com.example.foodexpress_2.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodexpress_2.Adapter.FoodListAdapter;
import com.example.foodexpress_2.Domain.ItemsDomain;
import com.example.foodexpress_2.databinding.ActivityListFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Food_Activity extends BaseActivity {
    ActivityListFoodBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int Categoryid;
    private String categorytitle;
    private String searchText;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityListFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
        setVariable();
    }

    private void setVariable() {
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Items");
        ArrayList<ItemsDomain> list=new ArrayList<>();

        Query query;

        if (isSearch){
            query=myRef.orderByChild("categorytitle").startAt(searchText).endAt(searchText+'\uf8ff');
        }else {
            query=myRef.orderByChild("Categoryid").equalTo(Categoryid);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(ItemsDomain.class));
                    }
                    if (list.size()>0){
                        binding.foodListView.setLayoutManager(new GridLayoutManager(List_Food_Activity.this,2));
                        adapterListFood=new FoodListAdapter(list);
                        binding.foodListView.setAdapter(adapterListFood);
                    }
                    //    binding.progressBar3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getIntentExtra() {
        Categoryid=getIntent().getIntExtra("Categoryid",0);
        categorytitle=getIntent().getStringExtra("Categorytitle");
        searchText=getIntent().getStringExtra("text");
        isSearch=getIntent().getBooleanExtra("isSearch",false);
        binding.titleTxt.setText(categorytitle);
        binding.backBtn.setOnClickListener(v -> finish());
    }
}