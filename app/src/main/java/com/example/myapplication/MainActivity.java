package com.example.myapplication;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

        WifiManager WifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String IPAddress = Formatter.formatIpAddress(WifiManager.getConnectionInfo().getIpAddress());

        address = findViewById(R.id.address);
        sendButton = findViewById(R.id.sendButton);
        results = findViewById(R.id.results);
        results.setText("IP-адрес вашего устройства:" + IPAddress);

        receiveButton = findViewById(R.id.receiveButton);
        Receiver receiver = new Receiver(port, this);
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

    @SuppressLint("SetTextI18n")
    public void setResults(int count) {
        results.setText(Integer.toString(count));
    }
}