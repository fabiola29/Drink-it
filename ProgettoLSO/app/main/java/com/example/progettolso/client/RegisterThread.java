package com.example.progettolso.client;

import android.util.Log;

import com.example.progettolso.model.SocketSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegisterThread implements Runnable{

    private volatile int flag_error;
    private String Password;
    private String Username;
    private Boolean Passed;

    public RegisterThread(String user, String pass, Boolean passed){
        Username = user;
        Password = pass;
        Passed = passed;
    }

    @Override
    public void run() {
        try {

            Log.e("Client", Username + " " + Password);

            if(Passed == true){
                SocketSingleton.getPrintWriter().write(Password);
                SocketSingleton.getPrintWriter().flush();

                Boolean flag = true;
                String message1 = null;
                SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
                while (flag) {
                    message1 = SocketSingleton.getBufferedReader().readLine();
                    if (message1 != null) {
                        flag = false;
                    }
                }
                Log.e("SERVER", message1);

                //controlle se il messaggio ricevuto dal server corrisponde ad una di queste stringhe
                if (message1.equals("se4")) {
                    flag_error = 4;
                } else {
                    if (message1.equals("se5")) {
                        flag_error = 5;
                    } else {
                        if (message1.equals("seok")) {
                            flag_error = 0;
                        }
                    }
                }
            }else {
                Boolean flag = true;

                SocketSingleton.getPrintWriter().write(Username);
                SocketSingleton.getPrintWriter().flush();

                String message = null;
                SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
                Log.e("Client", "sono prima del ciclo");
                while (flag) {
                    Log.e("Client", "sono nel ciclo");
                    message = SocketSingleton.getBufferedReader().readLine();
                    if (message != null) {
                        flag = false;
                    }
                }
                Log.e("SERVER", message);

                if (message.equals("se1")) {
                    //errore stringa lunga
                    flag_error = 1;
                } else {
                    //faccio il resto
                    if (message.equals("se2")) {
                        Log.e("Client", "sono nell'if");
                        flag_error = 2;
                    } else {
                        if (message.equals("se3")) {
                            flag_error = 3;
                        } else {

                            SocketSingleton.getPrintWriter().write(Password);
                            SocketSingleton.getPrintWriter().flush();

                            flag = true;
                            String message1 = null;
                            SocketSingleton.setBufferedReader(new BufferedReader(new InputStreamReader(SocketSingleton.getSocket().getInputStream())));
                            while (flag) {
                                message1 = SocketSingleton.getBufferedReader().readLine();
                                if (message1 != null) {
                                    flag = false;
                                }
                            }
                            Log.e("SERVER", message1);

                            //controlle se il messaggio ricevuto dal server corrisponde ad una di queste stringhe
                            if (message1.equals("se4")) {
                                flag_error = 4;
                            } else {
                                if (message1.equals("se5")) {
                                    flag_error = 5;
                                } else {
                                    if (message1.equals("seok")) {
                                        flag_error = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        return flag_error;
    }
}
