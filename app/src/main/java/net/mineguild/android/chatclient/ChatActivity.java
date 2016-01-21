package net.mineguild.android.chatclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import net.mineguild.android.chatclient.connection.ConnectionTask;
import net.mineguild.android.chatclient.connection.SocketHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatActivity extends AppCompatActivity {

    PrintWriter output;
    private AsyncTask connectionTask;

    private TextView chatView;
    private EditText chatInput;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatView = (TextView) findViewById(R.id.chatView);
        chatInput = (EditText) findViewById(R.id.chatText);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(chatInput.getText().toString());
                chatInput.getText().clear();
            }
        });
        try {
            output = new PrintWriter(SocketHandler.getSocket().getOutputStream());
            startConnectionTask();
        } catch (IOException e) {
            //BAD!
        }
    }

    public void startConnectionTask() {
        ConnectionTask task = new ConnectionTask(this);
        connectionTask = task.execute();
    }

    public void receiveMessage(String message) {
        chatView.append(message+"\n");
        scroll.fullScroll(View.FOCUS_DOWN);
    }

    public void sendMessage(final String message) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        output.write(message+"\n");
        output.flush();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
