package com.example.myapplication;

import android.content.BroadcastReceiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Sender implements Runnable {
    private final String message;
    private final String addr;
    private final int port;

    public Sender(String message, String addr, int port) {
        this.message = message;
        this.addr = addr;
        this.port = port;
    }
    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(),
                    InetAddress.getByName(addr), port);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
