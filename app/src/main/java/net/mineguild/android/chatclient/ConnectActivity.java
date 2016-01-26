package net.mineguild.android.chatclient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.mineguild.android.chatclient.connection.ConnectionResult;
import net.mineguild.android.chatclient.connection.InitialConnectTask;
import net.mineguild.android.chatclient.connection.SocketHandler;

import java.io.IOException;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity {

    private EditText addressEdit;
    private EditText portEdit;
    private EditText nickEdit;

    private final int UNCONNECTED = 0;
    private final int CONNECTING = 1;
    private final int CONNECTED = 2;
    private int state = UNCONNECTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Button connectButton = (Button) findViewById(R.id.button);
        updateConnectButton();
        addressEdit = (EditText) findViewById(R.id.addressText);
        portEdit = (EditText) findViewById(R.id.portText);
        nickEdit = (EditText) findViewById(R.id.nickEdit);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopConnect();
            }
        });
    }

    private void updateConnectButton() {
        Button connectButton = (Button) findViewById(R.id.button);
        if (state == CONNECTED) {
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }

    private void startStopConnect() {
        if (state == CONNECTED) {
            disconnect();
        } else if (state != CONNECTING) {
            String address = addressEdit.getText().toString();
            String port = portEdit.getText().toString();
            String nick = nickEdit.getText().toString();
            InitialConnectTask initialConnectTask = new InitialConnectTask(this);
            AsyncTask task = initialConnectTask.execute(address, port, nick);
            state = CONNECTING;
        }
    }

    private void disconnect() {
        if (state == CONNECTED) {
            SocketHandler.disconnect();
            state = UNCONNECTED;
            updateConnectButton();
        }
    }

    public void finishConnect(int result) {
        if (state == CONNECTING) {
            if (result == 0) {
                Toast.makeText(ConnectActivity.this, "Connected.", Toast.LENGTH_SHORT).show();
                Intent chatActivity = new Intent(this, ChatActivity.class);
                state = CONNECTED;
                updateConnectButton();
                startActivity(chatActivity);
            } else if (result == 1) {
                new AlertDialog.Builder(this).setMessage("Your name is already in use!");
                state = UNCONNECTED;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

