package com.example.progettolso.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progettolso.helper.ManagementCart;
import com.example.progettolso.Interface.ChangeNumberItemsListener;
import com.example.progettolso.R;
import com.example.progettolso.model.DrinkDomain;

import java.util.ArrayList;

public class CartAdaptor extends RecyclerView.Adapter<CartAdaptor.ViewHolder>{
    private ArrayList<DrinkDomain> drinkDomains;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdaptor(ArrayList<DrinkDomain> drinkDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.drinkDomains = drinkDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }



    @NonNull
    @Override
    public CartAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdaptor.ViewHolder holder, int position) {

        holder.title.setText(drinkDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(drinkDomains.get(position).getFee()));
        holder.num.setText(String.valueOf(drinkDomains.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(drinkDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberDrink(drinkDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberDrink(drinkDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return drinkDomains.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, feeEachItem, button_checkout;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_Cart);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.pic_Cart);
            totalEachItem = itemView.findViewById(R.id.totalItem);
            num = itemView.findViewById(R.id.numberItem);
            plusItem = itemView.findViewById(R.id.addCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);

        }
    }
}

