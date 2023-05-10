package com.example.progettolso.ui.drinks;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.progettolso.R;
import com.example.progettolso.helper.ManagementCart;
import com.example.progettolso.model.DrinkDomain;

public class Activity_show_detail extends AppCompatActivity {

    private TextView addToCart;
    private TextView title, fee, description;
    private ImageView picDrink;
    private DrinkDomain object;
    int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        addToCart = findViewById(R.id.button_addToCart);
        title = findViewById(R.id.show_detail_title);
        fee = findViewById(R.id.show_detail_fee);

        description = findViewById(R.id.show_detail_description);
        description.setMovementMethod(new ScrollingMovementMethod());

        picDrink = findViewById(R.id.pic_show_detail);

        managementCart = new ManagementCart(this);
        getBundle();

    }
    private void getBundle(){

        object = (DrinkDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(picDrink);

        title.setText(object.getTitle());
        fee.setText("€" + object.getFee());
        description.setText(object.getDescriptor());

        DrinkDomain drinkDomain = getIntent().getParcelableExtra("DrinkDomain");

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managementCart.insertDrink(object);
            }
        });
    }
}