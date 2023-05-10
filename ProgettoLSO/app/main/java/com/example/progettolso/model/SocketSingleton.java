package com.example.progettolso.model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketSingleton {

    private static Socket socket;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(java.net.Socket socket) {
        SocketSingleton.socket = socket;
    }

    public static PrintWriter getPrintWriter(){ return printWriter; }

    public static void setPrintWriter(PrintWriter pw){ printWriter = pw; }

    public static  BufferedReader getBufferedReader(){ return bufferedReader; }

    public static void setBufferedReader(BufferedReader br){ bufferedReader = br; }

}
