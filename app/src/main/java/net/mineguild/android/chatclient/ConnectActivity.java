package net.mineguild.android.chatclient;

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
import android.widget.TextView;

import net.mineguild.android.chatclient.connection.ConnectionResult;
import net.mineguild.android.chatclient.connection.InitialConnectTask;

import java.io.IOException;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity {

    public static Thread connectionThread;

    private EditText addressEdit;
    private EditText portEdit;

    private final int UNCONNECTED = 0;
    private final int CONNECTING = 1;
    private final int CONNECTED  = 2;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Button connectButton = (Button) findViewById(R.id.button);
        addressEdit = (EditText) findViewById(R.id.addressText);
        portEdit = (EditText) findViewById(R.id.portText);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnect();
            }
        });
    }

    private void startConnect(){
        String address = addressEdit.getText().toString();
        String port = portEdit.getText().toString();
        InitialConnectTask initialConnectTask = new InitialConnectTask(this);
    }

    public void finishConnect(ConnectionResult result){
        if(state == CONNECTING){
            if(result == ConnectionResult.SUCCESS){
                Intent chatActivity = new Intent(this, ChatActivity.class);
                chatActivity.putExtra("Socket", Socket.class);
                startActivity(chatActivity);
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


    public boolean onConnectClicked(){

        return true;
    }
}

