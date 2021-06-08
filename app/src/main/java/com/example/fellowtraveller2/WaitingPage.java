package com.example.fellowtraveller2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaitingPage extends AppCompatActivity {

    TextView userId, status;

    String userID;
    Bundle bundle;
    Boolean isDriver;
    String data;
    private TextView textViewData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_page);
        bundle = getIntent().getExtras();
        userID = bundle.getString("userID");
        isDriver = bundle.getBoolean("isDriver");
        data = bundle.getString("data");
        userId = findViewById(R.id.userId);
        status = findViewById(R.id.status);
        textViewData = findViewById(R.id.text_view_data);
        userId.setText(userID);
        if(isDriver){
            status.setText("Driver");
        } else{
            status.setText("User");
            textViewData.setText(data);
        }


    }
}