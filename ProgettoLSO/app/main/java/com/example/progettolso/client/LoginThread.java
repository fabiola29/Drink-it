package com.example.progettolso.client;

import android.util.Log;

import com.example.progettolso.model.SocketSingleton;
import com.example.progettolso.model.UserSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginThread implements Runnable{

    private volatile int flag_error;
    String Username;
    String Password;
    Boolean Pass_User;
    Boolean Pass_Status;
    String message = null;
    String message_username = null;
    String message_password = null;

    public LoginThread(String user, String pass,Boolean pass_status, Boolean pass_user){
        Username = user;
        Password = pass;
        Pass_Status = pass_status;
        Pass_User = pass_user;
    }

    @Override
    public void run() {
        if(!Pass_Status) {

            Log.e("Client", Username + " " + Password);

            // parte con username e password, dove si dovrebbero verificare tutti gli eventuali errori
            ScriviMessaggio(Username);
            Log.e("Client", Username);
            message_username = LeggiMessaggio();
            Log.e("SERVER", message_username);
            if(message_username.equals("se1")){
                flag_error = 1;
            }else if(message_username.equals("se2")){
                flag_error = 2;
            }else{
                //passiamo alla password
                ScriviMessaggio(Password);
                Log.e("Client", Password);
                message_password = LeggiMessaggio();
                Log.e("SERVER", message_password);
                if(message_password.equals("se3")){
                    flag_error = 3;
                }else if(message_password.equals("se4")){
                    flag_error = 4;
                } else if (message_password.equals("seerror")) {
                    flag_error = 5;
                }else if(message_password.equals("selog")){
                    flag_error = 6;
                }else if(message_password.equals("seok")){
                    UserSingleton.setLogged(true);
                    UserSingleton.setUsername(Username);
                    UserSingleton.setPassword(Password);
                    flag_error = 0;
                }
            }
        }else if(Pass_Status && Pass_User) {
            message_password = LeggiMessaggio();
            ScriviMessaggio(Password);
            Log.e("SERVER", Password);
            message_password = LeggiMessaggio();
            if (message_password.equals("se3")) {
                flag_error = 3;
            } else if (message_password.equals("se4")) {
                flag_error = 4;
            } else if (message_password.equals("seerror")) {
                flag_error = 5;
            } else if(message_password.equals("selog")){
                flag_error = 6;
            }else if (message_password.equals("seok")) {
                UserSingleton.setLogged(true);
                UserSingleton.setUsername(Username);
                UserSingleton.setPassword(Password);
                flag_error = 0;
            }
        }
        else if(Pass_Status) {
            Log.e("Client", "else con Pass Status");
            //ora inizia la parte con username e password, dove si dovrebbero verificare tutti gli eventuali errori
            message = LeggiMessaggio();
            Log.e("SERVER", message);
            ScriviMessaggio(Username);
            Log.e("Client", Username);
            message_username = LeggiMessaggio();
            if (message_username.equals("se1")) {
                flag_error = 1;
            } else if (message_username.equals("se2")) {
                flag_error = 2;
            } else {
                //passiamo alla password
                ScriviMessaggio(Password);
                Log.e("SERVER", Password);
                message_password = LeggiMessaggio();
                if (message_password.equals("se3")) {
                    flag_error = 3;
                } else if (message_password.equals("se4")) {
                    flag_error = 4;
                } else if (message_password.equals("seerror")) {
                    flag_error = 5;
                } else if(message_password.equals("selog")){
                    flag_error = 6;
                } else if (message_password.equals("seok")) {
                    UserSingleton.setLogged(true);
                    UserSingleton.setUsername(Username);
                    UserSingleton.setPassword(Password);
                    Log.e("Client", UserSingleton.getUsername()+" "+UserSingleton.getPassword());
                    flag_error = 0;
                }
            }
        }

    }

    public int getValue() {
        return flag_error;
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
}