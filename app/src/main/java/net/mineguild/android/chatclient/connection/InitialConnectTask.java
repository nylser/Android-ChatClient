package net.mineguild.android.chatclient.connection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import net.mineguild.android.chatclient.ConnectActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InitialConnectTask extends AsyncTask<String, Void, Integer> {

    private ConnectActivity listener;
    private Socket socket;

    private ProgressDialog connectingDialog;

    public InitialConnectTask(ConnectActivity listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        connectingDialog = new ProgressDialog(listener);
        connectingDialog.setMessage("Connecting...");
        connectingDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        String address = params[0];
        int port = Integer.parseInt(params[1]);
        String name = params[2];

        try {
            socket = new Socket(address, port);
            SocketHandler.setSocket(socket); // Inform SocketHandler of new Socket.
            name += "\n";
            socket.getOutputStream().write(name.getBytes("UTF-8"));
            socket.getOutputStream().flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (reader.readLine().equals("ok")) {
                return 0;
            } else {
                socket.close();
                return 1;
            }
        } catch (IOException e) {
            Log.e("ChatClient", "Can't open Socket", e);
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer connectionResult) {
        connectingDialog.dismiss();
        listener.finishConnect(connectionResult);
    }
}
