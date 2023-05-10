package com.example.progettolso.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.progettolso.MainActivity;
import com.example.progettolso.R;
import com.example.progettolso.client.FunctionThread;
import com.example.progettolso.databinding.FragmentHomeBinding;
import com.example.progettolso.model.UserSingleton;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    Intent intent;
    public static Button b_log;
    public static Button b_reg;
    public  Button b_dis;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        b_log = root.findViewById(R.id.activity_button_sign_in);
        b_reg = root.findViewById(R.id.activity_button_sign_up);
        b_dis = root.findViewById(R.id.activity_button_disconnect);

        if(UserSingleton.getLogged()){
            b_log.setVisibility(View.INVISIBLE);
            b_reg.setVisibility(View.INVISIBLE);
        }

        b_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FunctionThread("registrazione").start();
                intent = new Intent(getContext(), SingUpActivity.class);
                startActivity(intent);
            }
        });

        b_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FunctionThread("login").start();
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        b_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FunctionThread("exit").start();
                //resetta i bottoni
                HomeFragment.b_log.setVisibility(View.VISIBLE);
                HomeFragment.b_reg.setVisibility(View.VISIBLE);
                createAlertDialog("You have been disconnected... see you next time!");
                // faccio chiudere l'applicazione
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createAlertDialog(String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.header.getContext());
        builder.setCancelable(true);
        builder.setTitle("Disconnection");
        builder.setMessage(description);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getActivity().finish();
                System.exit(0);
            }
        });
        builder.show();
    }
}