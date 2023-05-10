package com.example.progettolso;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettolso.client.ConnectThread;
import com.google.android.material.textfield.TextInputLayout;

public class Activity_connection extends AppCompatActivity {

    Intent intent;
    public static Boolean flag_error;
    private Button b_conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        TextInputLayout e_addr = findViewById(R.id.activity_connection_text_input_address_layout);
        TextInputLayout e_port = findViewById(R.id.activity_connection_text_input_port_layout);
        b_conn = findViewById(R.id.button_conn);

        b_conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!e_addr.getEditText().getText().toString().equals("") && !e_port.getEditText().getText().toString().equals("")) {
                    flag_error = false;
                    ConnectThread c = new ConnectThread(e_addr.getEditText().getText().toString(), Integer.parseInt(e_port.getEditText().getText().toString()));
                    Thread t = new Thread(c);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (e_addr.getEditText().getText().toString().equals("172.20.10.2") && !flag_error) {

                        //Serve qualcosa che ritorni in questo caso il client
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Connection", true);
                        finish();
                        startActivity(intent);

                    } else {

                        createAlertDialog("The address or port entered is invalid.");
                    }
                }else{
                    createAlertDialog("You must first enter an address and a port.");
                }

            }
        });

    }
    public void createAlertDialog(String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_connection.this);
        builder.setCancelable(true);
        builder.setTitle("Errore di connessione");
        builder.setMessage(description);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}