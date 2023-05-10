package com.example.progettolso.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettolso.MainActivity;
import com.example.progettolso.R;
import com.example.progettolso.client.LoginThread;
import com.example.progettolso.model.UserSingleton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout e_user;
    TextInputLayout e_pass;
    Button b_log;
    Boolean pass_status = false;
    Boolean pass_user = false;
    KeyListener variable;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e_user = findViewById(R.id.fragment_sign_in_text_input_nickname_layout);
        e_pass = findViewById(R.id.fragment_sign_in_text_input_password_layout);

        variable = e_user.getEditText().getKeyListener();

        b_log = findViewById(R.id.fragment_sign_in_button_sign_in);
        b_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //thread login
                LoginThread l = new LoginThread(e_user.getEditText().getText().toString(),e_pass.getEditText().getText().toString(),pass_status,pass_user);
                Thread t = new Thread(l);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int flag_error = l.getValue();
                chooseAlertDialog(flag_error);
            }
        });
    }

    public void chooseAlertDialog(int flag){

        Log.e("Client","valore del flag error: "+flag);

        if(flag == 1) {
           // pass_status = true;

            e_user.getEditText().setKeyListener(variable);

            createAlertDialog("Username error! the username is too long");

        }else if(flag == 2){
          //  pass_status = true;

           // e_user.getEditText().setKeyListener(variable);

            createAlertDialog("Username error! username contains spaces");

        }else if(flag == 3){
             pass_user = true;
             pass_status = true;

             e_user.getEditText().setKeyListener(null);
            //Textbox dell'username bloccata

            createAlertDialog("Password error! the password is too long");

        }else if(flag == 4){
             pass_status = true;
             pass_user = true;

             e_user.getEditText().setKeyListener(null);
            //Textbox dell'username bloccata

            createAlertDialog("Password error! the password contains spaces");

        }else if(flag == 5){
            pass_status = true;
            pass_user = false;

            e_user.getEditText().setKeyListener(variable);
            //riporta le textbox normali

            createAlertDialog("Login Error! incorrect username or password");

        }else if(flag == 6){
            pass_status = true;
            pass_user = false;

            e_user.setFocusable(true);

            createAlertDialog("Login Error! This user is already logged in!");

        } else if(flag == 0){
            pass_user = true;

            TextView menu_username = MainActivity.header.findViewById(R.id.menu_username);
            menu_username.setText("Welcome back "+ UserSingleton.getUsername()+"!");

            HomeFragment.b_log.setVisibility(View.INVISIBLE);
            HomeFragment.b_reg.setVisibility(View.INVISIBLE);

            createAlertDialogSuccess("Login successful!");

        }
    }


    public void createAlertDialog(String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Login Notification");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Login Notification");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
