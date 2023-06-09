// Generated by view binder compiler. Do not edit!
package com.example.progettolso.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.progettolso.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSlideshowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView buttonCheckout;

  @NonNull
  public final ConstraintLayout fragmentCart;

  @NonNull
  public final EditText inputAddress;

  @NonNull
  public final EditText inputCardExpiry;

  @NonNull
  public final EditText inputCardNumber;

  @NonNull
  public final EditText inputCardPin;

  @NonNull
  public final EditText inputCell;

  @NonNull
  public final EditText inputCity;

  @NonNull
  public final EditText inputName;

  @NonNull
  public final EditText inputState;

  @NonNull
  public final EditText inputZip;

  @NonNull
  public final RecyclerView recyclerviewCart;

  @NonNull
  public final ScrollView scrollViewCart;

  @NonNull
  public final SwitchCompat switchDelivery;

  @NonNull
  public final TextView textHead;

  @NonNull
  public final TextView totalItem;

  @NonNull
  public final TextView totalSlideshow;

  @NonNull
  public final TextView tvCardDetails;

  @NonNull
  public final TextView tvCustomerDetails;

  @NonNull
  public final TextView tvDelivery;

  @NonNull
  public final TextView tvPickup;

  private FragmentSlideshowBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView buttonCheckout, @NonNull ConstraintLayout fragmentCart,
      @NonNull EditText inputAddress, @NonNull EditText inputCardExpiry,
      @NonNull EditText inputCardNumber, @NonNull EditText inputCardPin,
      @NonNull EditText inputCell, @NonNull EditText inputCity, @NonNull EditText inputName,
      @NonNull EditText inputState, @NonNull EditText inputZip,
      @NonNull RecyclerView recyclerviewCart, @NonNull ScrollView scrollViewCart,
      @NonNull SwitchCompat switchDelivery, @NonNull TextView textHead, @NonNull TextView totalItem,
      @NonNull TextView totalSlideshow, @NonNull TextView tvCardDetails,
      @NonNull TextView tvCustomerDetails, @NonNull TextView tvDelivery,
      @NonNull TextView tvPickup) {
    this.rootView = rootView;
    this.buttonCheckout = buttonCheckout;
    this.fragmentCart = fragmentCart;
    this.inputAddress = inputAddress;
    this.inputCardExpiry = inputCardExpiry;
    this.inputCardNumber = inputCardNumber;
    this.inputCardPin = inputCardPin;
    this.inputCell = inputCell;
    this.inputCity = inputCity;
    this.inputName = inputName;
    this.inputState = inputState;
    this.inputZip = inputZip;
    this.recyclerviewCart = recyclerviewCart;
    this.scrollViewCart = scrollViewCart;
    this.switchDelivery = switchDelivery;
    this.textHead = textHead;
    this.totalItem = totalItem;
    this.totalSlideshow = totalSlideshow;
    this.tvCardDetails = tvCardDetails;
    this.tvCustomerDetails = tvCustomerDetails;
    this.tvDelivery = tvDelivery;
    this.tvPickup = tvPickup;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSlideshowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSlideshowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_slideshow, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSlideshowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_checkout;
      TextView buttonCheckout = ViewBindings.findChildViewById(rootView, id);
      if (buttonCheckout == null) {
        break missingId;
      }

      ConstraintLayout fragmentCart = (ConstraintLayout) rootView;

      id = R.id.inputAddress;
      EditText inputAddress = ViewBindings.findChildViewById(rootView, id);
      if (inputAddress == null) {
        break missingId;
      }

      id = R.id.inputCardExpiry;
      EditText inputCardExpiry = ViewBindings.findChildViewById(rootView, id);
      if (inputCardExpiry == null) {
        break missingId;
      }

      id = R.id.inputCardNumber;
      EditText inputCardNumber = ViewBindings.findChildViewById(rootView, id);
      if (inputCardNumber == null) {
        break missingId;
      }

      id = R.id.inputCardPin;
      EditText inputCardPin = ViewBindings.findChildViewById(rootView, id);
      if (inputCardPin == null) {
        break missingId;
      }

      id = R.id.inputCell;
      EditText inputCell = ViewBindings.findChildViewById(rootView, id);
      if (inputCell == null) {
        break missingId;
      }

      id = R.id.inputCity;
      EditText inputCity = ViewBindings.findChildViewById(rootView, id);
      if (inputCity == null) {
        break missingId;
      }

      id = R.id.inputName;
      EditText inputName = ViewBindings.findChildViewById(rootView, id);
      if (inputName == null) {
        break missingId;
      }

      id = R.id.inputState;
      EditText inputState = ViewBindings.findChildViewById(rootView, id);
      if (inputState == null) {
        break missingId;
      }

      id = R.id.inputZip;
      EditText inputZip = ViewBindings.findChildViewById(rootView, id);
      if (inputZip == null) {
        break missingId;
      }

      id = R.id.recyclerview_cart;
      RecyclerView recyclerviewCart = ViewBindings.findChildViewById(rootView, id);
      if (recyclerviewCart == null) {
        break missingId;
      }

      id = R.id.scrollView_cart;
      ScrollView scrollViewCart = ViewBindings.findChildViewById(rootView, id);
      if (scrollViewCart == null) {
        break missingId;
      }

      id = R.id.switchDelivery;
      SwitchCompat switchDelivery = ViewBindings.findChildViewById(rootView, id);
      if (switchDelivery == null) {
        break missingId;
      }

      id = R.id.text_head;
      TextView textHead = ViewBindings.findChildViewById(rootView, id);
      if (textHead == null) {
        break missingId;
      }

      id = R.id.totalItem;
      TextView totalItem = ViewBindings.findChildViewById(rootView, id);
      if (totalItem == null) {
        break missingId;
      }

      id = R.id.total_slideshow;
      TextView totalSlideshow = ViewBindings.findChildViewById(rootView, id);
      if (totalSlideshow == null) {
        break missingId;
      }

      id = R.id.tvCardDetails;
      TextView tvCardDetails = ViewBindings.findChildViewById(rootView, id);
      if (tvCardDetails == null) {
        break missingId;
      }

      id = R.id.tvCustomerDetails;
      TextView tvCustomerDetails = ViewBindings.findChildViewById(rootView, id);
      if (tvCustomerDetails == null) {
        break missingId;
      }

      id = R.id.tvDelivery;
      TextView tvDelivery = ViewBindings.findChildViewById(rootView, id);
      if (tvDelivery == null) {
        break missingId;
      }

      id = R.id.tvPickup;
      TextView tvPickup = ViewBindings.findChildViewById(rootView, id);
      if (tvPickup == null) {
        break missingId;
      }

      return new FragmentSlideshowBinding((ConstraintLayout) rootView, buttonCheckout, fragmentCart,
          inputAddress, inputCardExpiry, inputCardNumber, inputCardPin, inputCell, inputCity,
          inputName, inputState, inputZip, recyclerviewCart, scrollViewCart, switchDelivery,
          textHead, totalItem, totalSlideshow, tvCardDetails, tvCustomerDetails, tvDelivery,
          tvPickup);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
