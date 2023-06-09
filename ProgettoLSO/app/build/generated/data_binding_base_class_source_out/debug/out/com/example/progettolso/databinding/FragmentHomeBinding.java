// Generated by view binder compiler. Do not edit!
package com.example.progettolso.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button activityButtonDisconnect;

  @NonNull
  public final Button activityButtonSignIn;

  @NonNull
  public final Button activityButtonSignUp;

  @NonNull
  public final TextView activityMainTextViewApp;

  @NonNull
  public final TextView activityTextViewNameApp;

  @NonNull
  public final LinearLayout linearLayout;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button activityButtonDisconnect, @NonNull Button activityButtonSignIn,
      @NonNull Button activityButtonSignUp, @NonNull TextView activityMainTextViewApp,
      @NonNull TextView activityTextViewNameApp, @NonNull LinearLayout linearLayout) {
    this.rootView = rootView;
    this.activityButtonDisconnect = activityButtonDisconnect;
    this.activityButtonSignIn = activityButtonSignIn;
    this.activityButtonSignUp = activityButtonSignUp;
    this.activityMainTextViewApp = activityMainTextViewApp;
    this.activityTextViewNameApp = activityTextViewNameApp;
    this.linearLayout = linearLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activity_button_disconnect;
      Button activityButtonDisconnect = ViewBindings.findChildViewById(rootView, id);
      if (activityButtonDisconnect == null) {
        break missingId;
      }

      id = R.id.activity_button_sign_in;
      Button activityButtonSignIn = ViewBindings.findChildViewById(rootView, id);
      if (activityButtonSignIn == null) {
        break missingId;
      }

      id = R.id.activity_button_sign_up;
      Button activityButtonSignUp = ViewBindings.findChildViewById(rootView, id);
      if (activityButtonSignUp == null) {
        break missingId;
      }

      id = R.id.activity_main_textView_app;
      TextView activityMainTextViewApp = ViewBindings.findChildViewById(rootView, id);
      if (activityMainTextViewApp == null) {
        break missingId;
      }

      id = R.id.activity_textView_nameApp;
      TextView activityTextViewNameApp = ViewBindings.findChildViewById(rootView, id);
      if (activityTextViewNameApp == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, activityButtonDisconnect,
          activityButtonSignIn, activityButtonSignUp, activityMainTextViewApp,
          activityTextViewNameApp, linearLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
