package com.example.progettolso.client;

import android.util.Log;

import com.example.progettolso.model.SocketSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FunctionThread extends Thread {

    String Function;
    String message;

    public FunctionThread(String valore){
        Function = valore;
    }

    @Override
    public void run() {
        if (Function.equals("registrazione")) {
            ScriviMessaggio("signin");
            message = LeggiMessaggio();
            Log.e("SERVER", message);
        } else if (Function.equals("login")) {
            ScriviMessaggio("login");
            message = LeggiMessaggio();
            Log.e("SERVER", message);
        } else if (Function.equals("exit")) {
            ScriviMessaggio("exit");
            message = LeggiMessaggio();
            Log.e("SERVER", message);
        } else if(Function.equals("drinks")) {
            ScriviMessaggio1("drinks");
            message = LeggiMessaggio1();
            Log.e("SERVER", message);
        }else if(Function.equals("cart")) {
            ScriviMessaggio1("cart");
            message = LeggiMessaggio1();
            Log.e("SERVER", message);
        }
        interrupt();
    }

    public String LeggiMessaggio(){
        Boolean flag = true;
        String message = null;
        try {
            SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
            while (flag) {
                Log.e("Client", "sono nel ciclo della lettura");
                message = SocketSingleton.getBufferedReader().readLine();
                if (message != null) {
                    flag = false;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return message;
    }

    public void ScriviMessaggio(String messaggio){
        SocketSingleton.getPrintWriter().write(messaggio);
        SocketSingleton.getPrintWriter().flush();
        Log.e("Client", "ho scritto");
    }

    public String LeggiMessaggio1(){
        Boolean flag = true;
        String messaggio1 = null;
        try {
            SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
            while (flag) {
                Log.e("Client", "Ho caricato");
                messaggio1 = SocketSingleton.getBufferedReader().readLine();
                if (messaggio1 != null) {
                    flag = false;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return messaggio1;
    }

    public void ScriviMessaggio1(String messaggio1){
        SocketSingleton.getPrintWriter().write(messaggio1);
        SocketSingleton.getPrintWriter().flush();
        Log.e("Client", "Carico i dati");
    }

}
