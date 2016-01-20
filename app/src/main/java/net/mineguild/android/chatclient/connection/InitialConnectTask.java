package net.mineguild.android.chatclient.connection;

import android.os.AsyncTask;
import android.util.Log;

import net.mineguild.android.chatclient.ConnectActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InitialConnectTask extends AsyncTask<String, Void, ConnectionResult> {

    private ConnectActivity listener;

    public InitialConnectTask(ConnectActivity listener){
        this.listener = listener;
    }

    @Override
    /**
     * params (params[0] Address, params[1] port, params[2] Nickname)
     */
    protected ConnectionResult doInBackground(String... params) {
        String address = params[0];
        int port = Integer.parseInt(params[1]);
        String name = "Nickname";
        try {
            Socket socket = new Socket(address, port);
            socket.getOutputStream().write(name.getBytes("UTF-8"));
            socket.getOutputStream().flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(reader.readLine().equals("ok")){
                return ConnectionResult.SUCCESS;
            } else {
                return ConnectionResult.NAME_USED;
            }
        } catch (IOException e){
            Log.e("ChatClient", "Can't open Socket", e);
            return ConnectionResult.IO_ERROR;
        }
    }

    @Override
    protected void onPostExecute(ConnectionResult connectionResult) {
        listener.finishConnect(connectionResult);
    }
}
