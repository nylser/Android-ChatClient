package net.mineguild.android.chatclient.connection;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

public class SocketHandler {

    private static Socket socket;


    private static InetAddress suspendedAddress;
    private static int suspendedPort;

    private static boolean isSuspended = false;
    private static UUID suspendedID;

    public static synchronized Socket getSocket() {
        return socket;
    }

    public static synchronized void setSocket(Socket socket) {
        SocketHandler.socket = socket;
        isSuspended = false;
    }

    /**
     * Disconnects using the designated server protocol (/bye)
     */
    public static synchronized void disconnect() {
        try {
            socket.getOutputStream().write("/bye\n".getBytes("UTF-8"));
            socket.getOutputStream().flush();
        } catch (IOException ignored) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("ChatClient", "Can't close Socket", e);
            }
        }
    }

    public static boolean isSuspended(){
        return isSuspended;
    }


    public static synchronized void suspendConnection() {
        if(isSuspended){
            return;
        }
        suspendedAddress = socket.getInetAddress();
        suspendedPort = socket.getPort();
        suspendedID = UUID.randomUUID();
        try {
            socket.getOutputStream().write(("/suspend " + suspendedID.toString() + "\n").getBytes("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = bufferedReader.readLine();
            if(response.equals("ok_dcnow")){
                bufferedReader.close();
                socket.close();
                isSuspended = true;
            } else if(response.equals("id_used")) {

            }
        } catch (IOException e){
            // TODO: What now?
        }
    }

    public static synchronized void returnConnection() {
        if(!isSuspended){
            return;
        }
        try {
            socket = new Socket(suspendedAddress, suspendedPort);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println("/return " + suspendedID.toString()+ "\n");
            String response = reader.readLine();
            if(response.equals("return_ok")){
                isSuspended = false;
            }
        } catch (IOException e){

        }
    }

}
