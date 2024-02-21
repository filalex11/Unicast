package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private EditText address;
    private Button sendButton, receiveButton;
    private TextView results;
    private static int port = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = findViewById(R.id.address);
        sendButton = findViewById(R.id.sendButton);
        results = findViewById(R.id.results);
        receiveButton = findViewById(R.id.receiveButton);
        Receiver receiver = new Receiver(port);
        Thread receiving = new Thread(receiver);
        receiving.start();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Hello!";
                String[] list = address.getText().toString().split("\n");
                for (String addr : list) {
                    //String addr = address.getText().toString();
                    Thread sending = new Thread(new Sender(message, addr, port));
                    sending.start();
                }
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.setText(receiver.getCount());
            }
        });


        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (receiver.wasChanged())
                    results.setText(receiver.sb);
            }
        });*/

        //receive();
    }
}