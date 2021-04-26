package com.example.library;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectBase extends Thread{
    private static final int default_port = 13302;
    private Socket socket;
    private String ip;
    private int port;
    private boolean bValid;
    private String message;
    DataOutputStream dataOutputStream = null;

    public ConnectBase(String ip) {
        this.ip = ip;
        this.port = default_port;
    }

    public ConnectBase(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    @Override
    public void run() {
        try{
            connectServer();
            if(bValid) {
                dataOutputStream = getOutputStream();
                dataOutputStream.write(this.message.getBytes());

                System.out.println(this.message);
                System.out.println("sent a message and close socket");
                Thread.sleep(1000);
                closeSocket();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void connectServer(){
        try {
            this.socket = new Socket(ip, port);

            if(socket.isConnected()){
                bValid = true;
            }else{
                bValid = false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    protected DataOutputStream getOutputStream() {
        try {
            DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
            return outputStream;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected void flushStream() {
        try {
            getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void closeSocket() {
        try{
            if ( socket.isConnected()) {
                socket.close();
                bValid = false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
