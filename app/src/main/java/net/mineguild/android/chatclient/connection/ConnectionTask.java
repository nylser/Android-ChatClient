package net.mineguild.android.chatclient.connection;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import net.mineguild.android.chatclient.ChatActivity;
import net.mineguild.android.chatclient.ConnectActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionTask extends AsyncTask<String, Void, Void> {

    private ChatActivity activity;

    public ConnectionTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(SocketHandler.getSocket().getInputStream()));
            while(!isCancelled()){
                final String text = input.readLine();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.receiveMessage(text);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
