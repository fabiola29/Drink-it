package com.example.progettolso.client;

import android.util.Log;

import com.example.progettolso.Activity_connection;
import com.example.progettolso.model.SocketSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectThread extends Thread{

    private String address;
    private int port;

    public ConnectThread(String addr, int port) {
        address = addr;
        this.port = port;
    }

    public void run() {
        try {

            SocketSingleton.setSocket(new Socket(address, port));
            SocketSingleton.setPrintWriter(new PrintWriter(SocketSingleton.getSocket().getOutputStream()));

            Log.e("Client","Richiedo connessione");
            SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
            Boolean flag = true;
            String message = null;
            while(flag){
                message = SocketSingleton.getBufferedReader().readLine();
                if(message != null) {
                    Log.e("SERVER",message);
                    flag = false;
                }
            }
            Log.e("Client","...sono fuori al while");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Client","CONNESSIONE NON RIUSCITA");
            Activity_connection.flag_error = true;
        }
        interrupt();
    }

}

