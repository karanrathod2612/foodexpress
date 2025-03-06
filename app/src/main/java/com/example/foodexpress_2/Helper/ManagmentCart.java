package com.example.foodexpress_2.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.foodexpress_2.Domain.ItemsDomain;

import java.util.ArrayList;


public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }

    public void insertFood(ItemsDomain item) {
        ArrayList<ItemsDomain> listpop = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            listpop.get(n).setNumberinCart(item.getNumberinCart());
        }else{
            listpop.add(item);
        }
        tinyDB.putListObject("CartList",listpop);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<ItemsDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public Double getTotalFee(){
        ArrayList<ItemsDomain> listItem=getListCart();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee=fee+(listItem.get(i).getPrice()*listItem.get(i).getNumberinCart());
        }
        return fee;
    }
    public void minusNumberItem(ArrayList<ItemsDomain> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumberinCart()==1){
            listItem.remove(position);
        }else{
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart()-1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.changed();
    }
    public  void plusNumberItem(ArrayList<ItemsDomain> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart()+1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.changed();
    }
}
