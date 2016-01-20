package net.mineguild.android.chatclient.connection;

import java.net.Socket;

public class SocketHandler {
    private static Socket socket;

    public static synchronized Socket getSocket() {
        return socket;
    }

    public static synchronized void setSocket(Socket socket) {
        SocketHandler.socket = socket;
    }

}
