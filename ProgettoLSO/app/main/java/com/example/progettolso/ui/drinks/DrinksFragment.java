package com.example.progettolso.ui.drinks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettolso.R;
import com.example.progettolso.adaptor.CategoryAdaptor;
import com.example.progettolso.adaptor.DrinkAdaptor;
import com.example.progettolso.client.FunctionThread;
import com.example.progettolso.databinding.FragmentDrinksBinding;
import com.example.progettolso.model.CategoryDomain;
import com.example.progettolso.model.DrinkDomain;
import com.example.progettolso.model.UserSingleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.exceptions.Neo4jException;
import org.neo4j.driver.internal.spi.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrinksFragment extends Fragment {

    private RecyclerView.Adapter adapter, adapter2, adapter3;
    private FragmentDrinksBinding binding;
    private TextView category, cocktail, shake, text_head;
    private RecyclerView recyclerViewcategory, recyclerViewcocktail, recyclerViewshake;
    private List<DrinkDomain> viewItems1 = new ArrayList<>();
    private List<CategoryDomain> viewItems2 = new ArrayList<>();
    private List<DrinkDomain> viewItems = new ArrayList<>();
    private ArrayList<DrinkDomain> drinkDomain = new ArrayList<>();
    private ProgressBar progressBar_drink;
    private ArrayList<CategoryDomain> categoryDomain = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DrinksViewModel drinksViewModel =
                new ViewModelProvider(this).get(DrinksViewModel.class);

        binding = FragmentDrinksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        text_head = root.findViewById(R.id.text_head);
        category = root.findViewById(R.id.fragment_drinks_category);
        cocktail = root.findViewById(R.id.fragment_drinks_Cocktail);
        shake = root.findViewById(R.id.fragment_drinks_Shake);

        recyclerViewcategory = root.findViewById(R.id.fragment_drinks_recyclerView_category);
        recyclerViewcocktail = root.findViewById(R.id.fragment_drinks_recyclerView_cocktail);
        recyclerViewshake = root.findViewById(R.id.fragment_drinks_recyclerView_shake);
        progressBar_drink = (ProgressBar) root.findViewById(R.id.progressBar_drink);

        checkUtenteLogin();

        recyclerViewCategory();

        recyclerViewCocktail();

        recyclerViewShake();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void checkUtenteLogin() {

        if (UserSingleton.getLogged()) {

            text_head.setVisibility(View.INVISIBLE);
            category.setVisibility(View.VISIBLE);
            cocktail.setVisibility(View.VISIBLE);
            shake.setVisibility(View.VISIBLE);
            recyclerViewcategory.setVisibility(View.VISIBLE);
            recyclerViewcocktail.setVisibility(View.VISIBLE);
            recyclerViewshake.setVisibility(View.VISIBLE);
            progressBar_drink.setVisibility(View.VISIBLE);

        } else {
            text_head.setVisibility(View.VISIBLE);
            category.setVisibility(View.VISIBLE);
            shake.setVisibility(View.INVISIBLE);
            cocktail.setVisibility(View.INVISIBLE);
            recyclerViewcategory.setVisibility(View.VISIBLE);
            recyclerViewcocktail.setVisibility(View.INVISIBLE);
            recyclerViewshake.setVisibility(View.INVISIBLE);
            progressBar_drink.setVisibility(View.INVISIBLE);
        }
    }

    private void recyclerViewCategory() {
        PrelevaDati_Category prelevaDati_Category = new PrelevaDati_Category();
        prelevaDati_Category.execute();
    }

    private void recyclerViewCocktail() {
        PrelevaDati_Cocktail prelevaDati_Cocktail = new PrelevaDati_Cocktail();
        prelevaDati_Cocktail.execute();
    }

    private void recyclerViewShake() {
        PrelevaDati_Shake prelevaDati_Shake = new PrelevaDati_Shake();
        prelevaDati_Shake.execute();
    }

    private void load_data_Cocktail() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewcocktail.setLayoutManager(linearLayoutManager);

        for (DrinkDomain d : drinkDomain) {
            viewItems.add(d);
        }
        adapter2 = new DrinkAdaptor(this, viewItems);
        recyclerViewcocktail.setAdapter(adapter2);
    }

    private void load_data_shake() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewshake.setLayoutManager(linearLayoutManager);

        for (DrinkDomain e : drinkDomain) {
            viewItems1.add(e);
        }
        adapter3 = new DrinkAdaptor(this, viewItems1);
        recyclerViewshake.setAdapter(adapter3);
    }

    private void load_data_category() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewcategory.setLayoutManager(linearLayoutManager);

        for (CategoryDomain c : categoryDomain) {
            viewItems2.add(c);
        }
        adapter = new CategoryAdaptor(this,viewItems2);
        recyclerViewcategory.setAdapter(adapter);

    }

    public class PrelevaDati_Cocktail extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            DaoDrinkDatabase daoDrinkDatabase = new DaoDrinkDatabase();
            drinkDomain = daoDrinkDatabase.getDrinkCocktail();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            load_data_Cocktail();
        }
    }

    public class PrelevaDati_Shake extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            DaoDrinkDatabase daoDrinkDatabase = new DaoDrinkDatabase();
            drinkDomain = daoDrinkDatabase.getDrinkShake();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar_drink.setVisibility(View.GONE);
            load_data_shake();
        }
    }

    public class PrelevaDati_Category extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            DaoCategoryDatabase daoCategoryDatabase = new DaoCategoryDatabase();
            categoryDomain = daoCategoryDatabase.getDrinkCategory();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar_drink.setVisibility(View.GONE);
            load_data_category();
        }
    }
}