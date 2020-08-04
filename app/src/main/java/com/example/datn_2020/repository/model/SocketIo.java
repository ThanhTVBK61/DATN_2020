package com.example.datn_2020.repository.model;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketIo {

    private static Socket INSTANCE;

    private SocketIo() {
    }

    public static Socket getInstance(){
        if(INSTANCE == null){
            try {
                INSTANCE =  IO.socket("http://10.0.2.2:3000/");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }
}
