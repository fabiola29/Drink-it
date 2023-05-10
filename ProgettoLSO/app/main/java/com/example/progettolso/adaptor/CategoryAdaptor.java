package com.example.progettolso.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progettolso.R;
import com.example.progettolso.model.CategoryDomain;
import com.example.progettolso.ui.drinks.DrinksFragment;

import java.util.List;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {

    private static final int TYPE = 1;
    private DrinksFragment context;
    private List<CategoryDomain> listRecycleItem;

    public CategoryAdaptor(DrinksFragment context, List<CategoryDomain> listRecycleItem) {
        this.context = context;
        this.listRecycleItem = listRecycleItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (position) {
            case TYPE:
            default:

                ViewHolder viewHolder = (CategoryAdaptor.ViewHolder) holder;
                CategoryDomain categoryDomain = (CategoryDomain) listRecycleItem.get(position);

                holder.categoryName.setText(categoryDomain.getTitle());

                int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(categoryDomain.getPic(), "drawable", holder.itemView.getContext().getPackageName());

                Glide.with(holder.itemView.getContext())
                        .load(drawableResourceId)
                        .into(holder.categoryPic);
        }
    }

    @Override
    public int getItemCount() {
       return listRecycleItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}
