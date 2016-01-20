package net.mineguild.android.chatclient.connection;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import net.mineguild.android.chatclient.ChatActivity;
import net.mineguild.android.chatclient.ConnectActivity;

import java.net.Socket;

public class ConnectionTask extends AsyncTask<String, Void, Void> {

    private ChatActivity activity;

    public ConnectionTask(ChatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {

        return null;
    }
}
