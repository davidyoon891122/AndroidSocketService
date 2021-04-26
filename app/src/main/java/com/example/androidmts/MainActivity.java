package com.example.androidmts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library.LoginCheck;

public class MainActivity extends AppCompatActivity {
    // socket status variables
    final int STATUS_DISCONNECTED = 0;
    final int STATUS_CONNECTED = 1;

    // server IP
    String ip = "10.131.150.171";
    // ConnectionService's binder
    SocketManager manager = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextID = findViewById(R.id.editTextID);
        final EditText editTextPW = findViewById(R.id.editTextPW);

        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        Button buttonJoin = findViewById(R.id.buttonJoin);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputID = editTextID.getText().toString();
                String inputPW = editTextPW.getText().toString();

                System.out.printf("Input ID : %s\n", inputID );
                System.out.printf("Input PW : %s\n", inputPW );

                if(inputID.equals("")){
                    Toast.makeText(getApplicationContext(), "Please write User ID.", Toast.LENGTH_LONG).show();
                }else if(inputPW.equals("")){
                    Toast.makeText(getApplicationContext(), "Please write User PW.", Toast.LENGTH_LONG).show();
                }


                LoginCheck loginChecker = new LoginCheck(inputID, inputPW);
                boolean loginResult = loginChecker.checkInfo();

                if (loginResult) {
                    Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }

                try{
                    connectToServer(v);
                    sendData(v);
                }catch (RemoteException ex) {
                    ex.printStackTrace();
                }


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("OnPause()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity onResume()");
        // get SocketManager instance
        manager = SocketManager.getInstance();
        System.out.println(manager.toString());
    }

    public void connectToServer(View v) throws RemoteException {
        System.out.println("Call connectToServer()");
        manager.setSocket(ip);
        manager.connect();
    }

    public void sendData(View v) throws RemoteException {
        if(manager.getStatus() == STATUS_CONNECTED) {
            System.out.println("sendData()");
            manager.send();
        } else {
            Toast.makeText(getApplicationContext(), "not connected to server", Toast.LENGTH_SHORT).show();
        }
    }

    public void receiveData(View v) throws RemoteException {
        if(manager.getStatus() == STATUS_CONNECTED) {
            manager.receive();
        }else{
            Toast.makeText(getApplicationContext(), "not connected to server", Toast.LENGTH_SHORT).show();
        }
    }


}
