package com.example.androidmts;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import androidx.annotation.Nullable;

public class ConnectionService extends Service {
    // socket status variables
    final int STATUS_DISCONNECTED = 0;
    final int STATUS_CONNECTED = 1;

    // set timeout
    final int TIME_OUT = 5000;

    private int status = STATUS_DISCONNECTED;
    private Socket socket = null;
    private SocketAddress socketAddress = null;
    private DataOutputStream outputStream = null;
    private DataInputStream inputStream = null;
    private int port = 13302;
    Handler handler;

    IConnectionService.Stub binder = new IConnectionService.Stub() {
        @Override
        public int getStatus() throws RemoteException {
            return status;
        }

        @Override
        public void setSocket(String ip) throws RemoteException {
            mySetSocket(ip);
        }

        @Override
        public void connect() throws RemoteException {
            myConnect();
        }

        @Override
        public void disconnect() throws RemoteException {
            myDisconnect();
        }

        @Override
        public void send() throws RemoteException {
            mySend();
        }

        @Override
        public void receive() throws RemoteException {
            myReceive();
        }
    };

    public ConnectionService(){}


    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        System.out.println("ConnectionService onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("ConnectionService onStartCommand()");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("ConnectionService onDestroy()");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("ConnectionService onUnbind()");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("ConnectionService onBind()");
        return binder;
    }

    void mySetSocket(String ip) {
        socketAddress = new InetSocketAddress(ip, port);
        System.out.println("ConnectionService mySetSocket()");
    }

    void myConnect() {
        System.out.println("ConnectionService onConnect()");
        socket = new Socket();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket.connect(socketAddress, TIME_OUT);
                    outputStream = new DataOutputStream(socket.getOutputStream());
                    inputStream = new DataInputStream(socket.getInputStream());
                    System.out.println("ConnectionService create Streams");
                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), String.format("%s", e.toString()), Toast.LENGTH_SHORT).show();
                    _runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Socket Connection Error ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                status = STATUS_CONNECTED;
            }
        }).start();
    }

    void myDisconnect() {
        try{
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        status = STATUS_DISCONNECTED;
    }

    void mySend() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream.write("Hello World".getBytes("UTF-8"));
                    outputStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    void myReceive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("ConnectionService myReceive()");
                    System.out.println("============================================");
                    System.out.println(inputStream.read(new byte[256]));
                    System.out.println("============================================");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }


    private void _runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

}
