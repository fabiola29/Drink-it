package com.example.progettolso.ui.slideshow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettolso.client.FunctionThread;
import com.example.progettolso.helper.DrinkDB;
import com.example.progettolso.helper.ManagementCart;
import com.example.progettolso.Interface.ChangeNumberItemsListener;
import com.example.progettolso.R;
import com.example.progettolso.adaptor.CartAdaptor;
import com.example.progettolso.databinding.FragmentSlideshowBinding;
import com.example.progettolso.model.DrinkDomain;
import com.example.progettolso.model.UserSingleton;
import com.example.progettolso.ui.drinks.DaoDrinkDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList_Cart;
    private ManagementCart managementCart;
    private TextView totalFee,total,text_head;
    private TextView button_checkout;
    private ScrollView scrollView;
    private EditText inputName, inputAddress, inputCity, inputState, inputCap,inputCardNumber, inputCardExpiry, inputCardPin,inputCell;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private DrinkDomain object;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        totalFee = root.findViewById(R.id.totalItem);
        scrollView = root.findViewById(R.id.scrollView_cart);
        recyclerViewList_Cart = root.findViewById(R.id.recyclerview_cart);
        total = root.findViewById(R.id.total_slideshow);
        text_head = root.findViewById(R.id.text_head);
        button_checkout = root.findViewById(R.id.button_checkout);

        inputName = root.findViewById(R.id.inputName);
        inputName.setText(UserSingleton.getUsername());

        inputAddress = root.findViewById(R.id.inputAddress);
        inputCity = root.findViewById(R.id.inputCity);
        inputState = root.findViewById(R.id.inputState);
        inputCap = root.findViewById(R.id.inputZip);
        inputCardNumber = root.findViewById(R.id.inputCardNumber);
        inputCardExpiry = root.findViewById(R.id.inputCardExpiry);
        inputCardPin = root.findViewById(R.id.inputCardPin);
        inputCell = root.findViewById(R.id.inputCell);
        switchDelivery = root.findViewById(R.id.switchDelivery);

        checkUtenteLogin();

        managementCart = new ManagementCart(getContext());

        initList();

        CalculatedCart();

        listenToClickEvents();

        return root;
    }

    public void checkUtenteLogin() {
        if (UserSingleton.getLogged()) {
            text_head.setVisibility(View.INVISIBLE);
            total.setVisibility(View.VISIBLE);
            button_checkout.setVisibility(View.VISIBLE);
            recyclerViewList_Cart.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            totalFee.setVisibility(View.VISIBLE);
        }else{
            text_head.setVisibility(View.VISIBLE);
            total.setVisibility(View.INVISIBLE);
            button_checkout.setVisibility(View.INVISIBLE);
            recyclerViewList_Cart.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
            totalFee.setVisibility(View.INVISIBLE);
        }
    }

    private void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerViewList_Cart.setLayoutManager(linearLayoutManager);
        adapter = new CartAdaptor(managementCart.getListCart(),getContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculatedCart();
            }
        });
        recyclerViewList_Cart.setAdapter((RecyclerView.Adapter) adapter);
    }

    private void CalculatedCart(){

        double total = Math.round(managementCart.getTotalFee()*100.00)/100.00;

        totalFee.setText("€"+String.format("%.2f", total));
    }

    private DrinkDomain drinkDomain;

    public void listenToClickEvents() {
        button_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputName.getText().toString())) {
                    inputName.setError("Please enter name");
                    return;
                } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
                    inputAddress.setError("Please enter address");
                    return;
                } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
                    inputCity.setError("Please enter city");
                    return;
                } else if (isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
                    inputState.setError("Please enter cap");
                    return;
                } else if (TextUtils.isEmpty(inputCardNumber.getText().toString())) {
                    inputCardNumber.setError("Please enter card number");
                    return;
                } else if (TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
                    inputCardExpiry.setError("Please enter card expiry");
                    return;
                } else if (TextUtils.isEmpty(inputCardPin.getText().toString())) {
                    inputCardPin.setError("Please enter card pin/cvv");
                    return;
                } else if (TextUtils.isEmpty(inputCell.getText().toString())) {
                    inputCell.setError("Please enter your cell number");
                    return;
                }

                object = (DrinkDomain) getActivity().getIntent().getSerializableExtra("object");
                managementCart.removeDrink(object);

            }
        });
        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputAddress.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputCap.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                } else {
                    inputAddress.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputCap.setVisibility(View.GONE);
                    isDeliveryOn = false;
                }
            }
        });
    }
}