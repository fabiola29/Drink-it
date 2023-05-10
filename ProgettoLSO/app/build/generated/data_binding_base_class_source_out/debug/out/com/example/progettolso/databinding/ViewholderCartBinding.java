// Generated by view binder compiler. Do not edit!
package com.example.progettolso.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.progettolso.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewholderCartBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView addCartBtn;

  @NonNull
  public final TextView feeEachItem;

  @NonNull
  public final ImageButton minusCartBtn;

  @NonNull
  public final TextView numberItem;

  @NonNull
  public final ImageView picCart;

  @NonNull
  public final TextView textView19;

  @NonNull
  public final TextView titleCart;

  private ViewholderCartBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView addCartBtn,
      @NonNull TextView feeEachItem, @NonNull ImageButton minusCartBtn,
      @NonNull TextView numberItem, @NonNull ImageView picCart, @NonNull TextView textView19,
      @NonNull TextView titleCart) {
    this.rootView = rootView;
    this.addCartBtn = addCartBtn;
    this.feeEachItem = feeEachItem;
    this.minusCartBtn = minusCartBtn;
    this.numberItem = numberItem;
    this.picCart = picCart;
    this.textView19 = textView19;
    this.titleCart = titleCart;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewholderCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewholderCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.viewholder_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewholderCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addCartBtn;
      ImageView addCartBtn = ViewBindings.findChildViewById(rootView, id);
      if (addCartBtn == null) {
        break missingId;
      }

      id = R.id.feeEachItem;
      TextView feeEachItem = ViewBindings.findChildViewById(rootView, id);
      if (feeEachItem == null) {
        break missingId;
      }

      id = R.id.minusCartBtn;
      ImageButton minusCartBtn = ViewBindings.findChildViewById(rootView, id);
      if (minusCartBtn == null) {
        break missingId;
      }

      id = R.id.numberItem;
      TextView numberItem = ViewBindings.findChildViewById(rootView, id);
      if (numberItem == null) {
        break missingId;
      }

      id = R.id.pic_Cart;
      ImageView picCart = ViewBindings.findChildViewById(rootView, id);
      if (picCart == null) {
        break missingId;
      }

      id = R.id.textView19;
      TextView textView19 = ViewBindings.findChildViewById(rootView, id);
      if (textView19 == null) {
        break missingId;
      }

      id = R.id.title_Cart;
      TextView titleCart = ViewBindings.findChildViewById(rootView, id);
      if (titleCart == null) {
        break missingId;
      }

      return new ViewholderCartBinding((ConstraintLayout) rootView, addCartBtn, feeEachItem,
          minusCartBtn, numberItem, picCart, textView19, titleCart);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}