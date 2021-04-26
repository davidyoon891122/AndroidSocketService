package com.example.androidmts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library.ConnectBase;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);
        Button buttonDisplay = findViewById(R.id.buttonDisplay);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                if(message.equals("")){
                    Toast.makeText(getApplicationContext(), "Can not send a empty message.", Toast.LENGTH_LONG).show();
                }

                sendMessage(message);

            }
        });


    }



    public void sendMessage(String message){
        String ip = "10.131.150.171";
        try{
            ConnectBase connectBase = new ConnectBase(ip, 13302);
            connectBase.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
