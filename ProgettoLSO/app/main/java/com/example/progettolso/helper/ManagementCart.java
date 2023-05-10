package com.example.progettolso.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.progettolso.Interface.ChangeNumberItemsListener;
import com.example.progettolso.model.DrinkDomain;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private DrinkDB drinkDB;


    public ManagementCart(Context context){
        this.context = context;
        this.drinkDB = new DrinkDB(context);
    }

    public void insertDrink(DrinkDomain item){
        ArrayList<DrinkDomain> listDrink = getListCart();
        boolean existAlready = false;
        int n =0;
        for(int i =0; i<listDrink.size(); i++){
            if(listDrink.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready) {
            listDrink.get(n).setNumberInCart(item.getNumberInCart());
        }else{
            listDrink.add(item);
        }
        drinkDB.putListObject("CartList",listDrink);
        Toast.makeText(context,"Added to your cart", Toast.LENGTH_SHORT).show();
    }

    public void removeDrink(DrinkDomain item){
        ArrayList<DrinkDomain> listDrink = getListCart();
        for(int i =0; i<listDrink.size(); i++) {
            drinkDB.remove("CartList");
        }
        Toast.makeText(context,"Registered order!!!", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<DrinkDomain> getListCart(){
        return drinkDB.getListObject("CartList");
    }

    public void  plusNumberDrink(ArrayList<DrinkDomain> listDrink, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listDrink.get(position).setNumberInCart(listDrink.get(position).getNumberInCart()+1);
        drinkDB.putListObject("CartList", listDrink);
        changeNumberItemsListener.changed();
    }

    public void  minusNumberDrink(ArrayList<DrinkDomain> listDrink, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if(listDrink.get(position).getNumberInCart()==1){
            listDrink.remove(position);
        }else{
            listDrink.get(position).setNumberInCart(listDrink.get(position).getNumberInCart()-1);
        }
        drinkDB.putListObject("CartList", listDrink);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<DrinkDomain> listDrink = getListCart();
        double fee = 0;
        for (int i = 0; i< listDrink.size(); i++){
            fee = fee + (listDrink.get(i).getFee() * listDrink.get(i).getNumberInCart());
        }
        return fee;
    }
}