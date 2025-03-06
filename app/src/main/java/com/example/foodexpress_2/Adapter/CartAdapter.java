package com.example.foodexpress_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodexpress_2.Domain.ItemsDomain;
import com.example.foodexpress_2.Helper.ChangeNumberItemsListener;
import com.example.foodexpress_2.Helper.ManagmentCart;
import com.example.foodexpress_2.R;
import com.example.foodexpress_2.databinding.ViewholderCartBinding;
import com.example.foodexpress_2.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<ItemsDomain> listItemSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<ItemsDomain> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart=new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        @NonNull ViewholderCartBinding binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
//        return new Viewholder(binding);

        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, int position) {
        holder.titletxt.setText(listItemSelected.get(position).getTitle());
        holder.feeEachItem.setText("₹"+ (listItemSelected.get(position).getNumberinCart()*listItemSelected.get(position).getPrice()));
        holder.totaleachitem.setText(listItemSelected.get(position).getNumberinCart()+"*₹"+
                (listItemSelected.get(position).getPrice()));
        holder.numberItemTxt.setText(listItemSelected.get(position).getNumberinCart()+"");

        RequestOptions requestOptions =  new RequestOptions();
        requestOptions  =requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getpicUrl().get(0))
                .apply(requestOptions)
                .into(holder.pic);

        holder.pluscartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.plusNumberItem(listItemSelected, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minuscartbtn.setOnClickListener(v -> {
            managmentCart.minusNumberItem(listItemSelected, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            });
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView titletxt,feeEachItem,totaleachitem,pluscartbtn,numberItemTxt,minuscartbtn;

//        ViewholderCartBinding binding;

        public Viewholder(@NonNull View itemview) {
            super(itemview);
            titletxt = itemview.findViewById(R.id.titleTxt);
            feeEachItem = itemview.findViewById(R.id.feeEachItem);
            totaleachitem = itemview.findViewById(R.id.totaleachitem);
            pluscartbtn = itemview.findViewById(R.id.pluscartbtn);
            numberItemTxt = itemview.findViewById(R.id.numberItemTxt);
            minuscartbtn = itemview.findViewById(R.id.minuscartbtn);
            pic = itemview.findViewById(R.id.pic);
//            super(binding.getRoot());
//            this.binding = binding;
        }
    }
}
