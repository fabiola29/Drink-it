// Generated by view binder compiler. Do not edit!
package com.example.progettolso.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.progettolso.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDrinksBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout fragmentDrink1;

  @NonNull
  public final TextView fragmentDrinksCategory;

  @NonNull
  public final TextView fragmentDrinksCocktail;

  @NonNull
  public final RecyclerView fragmentDrinksRecyclerViewCategory;

  @NonNull
  public final RecyclerView fragmentDrinksRecyclerViewCocktail;

  @NonNull
  public final RecyclerView fragmentDrinksRecyclerViewShake;

  @NonNull
  public final TextView fragmentDrinksShake;

  @NonNull
  public final TextView fragmentDrinksSubtitle;

  @NonNull
  public final TextView fragmentDrinksWelcom;

  @NonNull
  public final ProgressBar progressBarDrink;

  @NonNull
  public final TextView textHead;

  private FragmentDrinksBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout fragmentDrink1, @NonNull TextView fragmentDrinksCategory,
      @NonNull TextView fragmentDrinksCocktail,
      @NonNull RecyclerView fragmentDrinksRecyclerViewCategory,
      @NonNull RecyclerView fragmentDrinksRecyclerViewCocktail,
      @NonNull RecyclerView fragmentDrinksRecyclerViewShake, @NonNull TextView fragmentDrinksShake,
      @NonNull TextView fragmentDrinksSubtitle, @NonNull TextView fragmentDrinksWelcom,
      @NonNull ProgressBar progressBarDrink, @NonNull TextView textHead) {
    this.rootView = rootView;
    this.fragmentDrink1 = fragmentDrink1;
    this.fragmentDrinksCategory = fragmentDrinksCategory;
    this.fragmentDrinksCocktail = fragmentDrinksCocktail;
    this.fragmentDrinksRecyclerViewCategory = fragmentDrinksRecyclerViewCategory;
    this.fragmentDrinksRecyclerViewCocktail = fragmentDrinksRecyclerViewCocktail;
    this.fragmentDrinksRecyclerViewShake = fragmentDrinksRecyclerViewShake;
    this.fragmentDrinksShake = fragmentDrinksShake;
    this.fragmentDrinksSubtitle = fragmentDrinksSubtitle;
    this.fragmentDrinksWelcom = fragmentDrinksWelcom;
    this.progressBarDrink = progressBarDrink;
    this.textHead = textHead;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDrinksBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDrinksBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_drinks, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDrinksBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout fragmentDrink1 = (ConstraintLayout) rootView;

      id = R.id.fragment_drinks_category;
      TextView fragmentDrinksCategory = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksCategory == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_Cocktail;
      TextView fragmentDrinksCocktail = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksCocktail == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_recyclerView_category;
      RecyclerView fragmentDrinksRecyclerViewCategory = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksRecyclerViewCategory == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_recyclerView_cocktail;
      RecyclerView fragmentDrinksRecyclerViewCocktail = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksRecyclerViewCocktail == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_recyclerView_shake;
      RecyclerView fragmentDrinksRecyclerViewShake = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksRecyclerViewShake == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_Shake;
      TextView fragmentDrinksShake = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksShake == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_subtitle;
      TextView fragmentDrinksSubtitle = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksSubtitle == null) {
        break missingId;
      }

      id = R.id.fragment_drinks_welcom;
      TextView fragmentDrinksWelcom = ViewBindings.findChildViewById(rootView, id);
      if (fragmentDrinksWelcom == null) {
        break missingId;
      }

      id = R.id.progressBar_drink;
      ProgressBar progressBarDrink = ViewBindings.findChildViewById(rootView, id);
      if (progressBarDrink == null) {
        break missingId;
      }

      id = R.id.text_head;
      TextView textHead = ViewBindings.findChildViewById(rootView, id);
      if (textHead == null) {
        break missingId;
      }

      return new FragmentDrinksBinding((ConstraintLayout) rootView, fragmentDrink1,
          fragmentDrinksCategory, fragmentDrinksCocktail, fragmentDrinksRecyclerViewCategory,
          fragmentDrinksRecyclerViewCocktail, fragmentDrinksRecyclerViewShake, fragmentDrinksShake,
          fragmentDrinksSubtitle, fragmentDrinksWelcom, progressBarDrink, textHead);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
