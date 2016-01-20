package net.mineguild.android.chatclient.connection;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import net.mineguild.android.chatclient.ConnectActivity;

import java.net.Socket;

public class ConnectTask extends AsyncTask<String, Void, Socket> {

    private ConnectActivity activity;

    public ConnectTask(ConnectActivity activity){

        this.activity = activity;
    }

    ProgressDialog asyncDialog = new ProgressDialog(activity);

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        asyncDialog.setMessage("Connecting...");
        asyncDialog.show();
    }

    @Override
    protected Socket doInBackground(String... params) {
        String host = params[0];
        int port = Integer.parseInt(params[1]);
        String name = params[2];

        return null;
    }
}
