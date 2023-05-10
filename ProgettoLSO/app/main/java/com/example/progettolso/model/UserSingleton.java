package com.example.progettolso.model;

public class UserSingleton {

    private static String Username;
    private static String Password;
    private static boolean Logged;

    public static boolean getLogged() {
        return Logged;
    }

    public static void setLogged(boolean logged) {
        UserSingleton.Logged = logged;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        UserSingleton.Username = username;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        UserSingleton.Password = password;
    }

}
