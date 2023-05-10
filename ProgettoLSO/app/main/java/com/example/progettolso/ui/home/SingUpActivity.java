package com.example.progettolso.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettolso.R;
import com.example.progettolso.client.FunctionThread;
import com.example.progettolso.client.RegisterThread;
import com.google.android.material.textfield.TextInputLayout;

public class SingUpActivity extends AppCompatActivity {

    Button b_reg1,b_annulla;
    TextInputLayout e_username;
    TextInputLayout e_password;
    Boolean passed = false;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        e_username = findViewById(R.id.fragment_sign_up_text_input_nickname_layout);
        e_password = findViewById(R.id.fragment_sign_up_text_input_password_layout);
        b_reg1 = findViewById(R.id.fragment_sign_up_button_sign_up);
        b_annulla = findViewById(R.id.fragment_sign_up_button_Annull);

        b_reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterThread r = new RegisterThread(e_username.getEditText().getText().toString(),e_password.getEditText().getText().toString(),passed);
                Thread t = new Thread(r);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int flag_error = r.getValue();
                chooseAlertDialog(flag_error);
            }
        });

        b_annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FunctionThread("exit").start();

                finish();
            }
        });
    }

    public void chooseAlertDialog(int flag){

        Log.e("Client","valore del flag error: "+flag);

        if(flag == 1) {

            createAlertDialog("Username error! the username is too long");

        }else if(flag == 2){

            createAlertDialog("Username error! username contains spaces");

        }else if(flag == 3){

            createAlertDialog("Username error! the username is already taken");

        }else if(flag == 4){

            passed = true;
            createAlertDialog("Password error! the password is too long! Try again with a new password");

        }else if(flag == 5){

            passed = true;
            createAlertDialog("Password error! the password contains spaces! Try again to enter a new password");

        }else if(flag == 0){

            passed = true;
            createAlertDialogSuccess("Registration was successful! to continue press ok");

        }
    }

    public void createAlertDialog(String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(SingUpActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Registration Notification");
        builder.setMessage(description);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void createAlertDialogSuccess(String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(SingUpActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Registration Notification");
        builder.setMessage(description);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

}
