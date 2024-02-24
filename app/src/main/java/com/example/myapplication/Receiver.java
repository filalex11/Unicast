package com.example.myapplication;

import android.os.Build;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Receiver implements Runnable {
    private int port;
    private int count;
    //public StringBuilder sb;
    //public TextView results;

    private final MainActivity mainActivity;

    public Receiver(int port, MainActivity activity) {
        mainActivity = activity;
        this.port = port;
        //sb = null;
        count = 0;
        //this.results = results;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        byte[] buf = new byte[100];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        //sb = new StringBuilder();
        try {
            socket = new DatagramSocket(port);
            while (true) {
                socket.receive(packet);
                count++;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mainActivity.setResults(count);
                }
                //sb.append("Hello");
                //sb.append(Arrays.toString(packet.getData()));
                //sb.append("\n");
                //results.setText(sb);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCount() {
        return new String(String.valueOf(count));
    }
}
