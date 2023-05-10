package com.example.progettolso.adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progettolso.R;
import com.example.progettolso.model.DrinkDomain;
import com.example.progettolso.model.UserSingleton;
import com.example.progettolso.ui.drinks.Activity_show_detail;
import com.example.progettolso.ui.drinks.DrinksFragment;

import java.util.List;

public class DrinkAdaptor extends RecyclerView.Adapter<DrinkAdaptor.ViewHolder> {

    private static final int TYPE = 1;
    private DrinksFragment context;
    private List<DrinkDomain> listRecycleItem;

    public DrinkAdaptor(DrinksFragment context, List<DrinkDomain> listRecycleItem) {
        this.context = context;
        this.listRecycleItem = listRecycleItem;

    }

    @NonNull
    @Override
    public DrinkAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case TYPE:
            default:
                View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_drink, parent, false);
                return new DrinkAdaptor.ViewHolder(layoutView);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (position) {
            case TYPE:
            default:

                ViewHolder viewHolder = (ViewHolder) holder;
                DrinkDomain drinkDomain = (DrinkDomain) listRecycleItem.get(position);

                holder.title.setText(drinkDomain.getTitle());
                holder.fee.setText(String.valueOf(drinkDomain.getFee()));
                holder.descriptor.setText(drinkDomain.getDescriptor());

                int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(drinkDomain.getPic(), "drawable", holder.itemView.getContext().getPackageName());

                 Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.pic);

                holder.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(), Activity_show_detail.class);
                        intent.putExtra("object", drinkDomain);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
         return listRecycleItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, fee, descriptor;

        ImageView pic;

        Button addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            pic = itemView.findViewById(R.id.pic);
            descriptor = itemView.findViewById(R.id.descriptor);
            addBtn = itemView.findViewById(R.id.add);

            if (UserSingleton.getLogged()) {

                descriptor.setVisibility(View.INVISIBLE);

            } else {

                descriptor.setVisibility(View.INVISIBLE);

            }
        }
    }
}

