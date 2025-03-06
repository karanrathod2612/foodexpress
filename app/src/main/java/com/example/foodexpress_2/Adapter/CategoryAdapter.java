package com.example.foodexpress_2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodexpress_2.Domain.CategoryDomain;
import com.example.foodexpress_2.Activity.List_Food_Activity;
import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    private ArrayList<CategoryDomain>items;
    private Context context;

    public CategoryAdapter(ArrayList<CategoryDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new Viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTxt.setText(items.get(position).getTitle());

//
        switch (position){
            case 0:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 1:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 2:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 3:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 4:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 5:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 6:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
            case 7:{holder.pic.setBackgroundResource(R.drawable.cat1_background);break;}
        }

        @SuppressLint("DiscouragedApi") int drawableResourceId = context.getResources().getIdentifier(items.get(position).getPicUrl()
        ,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(context)
           .load(items.get(position).getPicUrl())
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,List_Food_Activity.class);
                intent.putExtra("Categoryid",items.get(position).getId());
                intent.putExtra("Categorytitle",items.get(position).getTitle());
                context.startActivity(intent);
            }
        });
   }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;
//        ViewholderCategoryBinding binding;

        public Viewholder(@NonNull View itemView) {
//            super(binding.getRoot());
            super(itemView);
            titleTxt = itemView.findViewById(R.id.cattitle);
            pic = itemView.findViewById(R.id.catpic);
//            this.binding = binding;
        }
    }
}
