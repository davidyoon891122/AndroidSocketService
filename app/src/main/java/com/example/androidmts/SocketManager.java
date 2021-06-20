package com.example.androidmts;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class SocketManager extends Application {
    private static final SocketManager instance = new SocketManager();
    private static Context context = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("SocketManager onServiceConnected()");
            binder = IConnectionService.Stub.asInterface(service);

            System.out.print("Binder :");
            System.out.println(binder);
            instance.setBinder(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("SocketManager onServiceDisconnected()");
        }
    };
    private IConnectionService binder = null;

    public SocketManager() {
        System.out.println("SocketManager()");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("SocketManager onCreate()");

        context = getApplicationContext();

        Intent intent = new Intent(context, ConnectionService.class);
        context.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public static SocketManager getInstance() {
        return instance;
    }

    public void setBinder(IConnectionService binder){
        this.binder = binder;
    }

    int getStatus() throws RemoteException {
        return binder.getStatus();
    }

    void setSocket(String ip) throws RemoteException {
        System.out.println("SocketManger setSocket()");
        binder.setSocket(ip);
    }

    void connect() throws RemoteException {
        binder.connect();
    }

    void disconnect() throws RemoteException {
        binder.disconnect();
    }

    void send() throws RemoteException {
        binder.send();
    }

    void receive() throws RemoteException {
        binder.receive();
    }

    @Override
    public String toString() {
        return "SocketManager{" +
                "connection=" + connection +
                ", binder=" + binder +
                '}';
    }
}
