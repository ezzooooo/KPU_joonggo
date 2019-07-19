package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ListView messageListView;
    private MessageListAdapter receive_messageListAdapter, send_messageListAdapter;
    private List<Message> messageList;

    private String loginID;
    private String check = "receive";

    private TextView message_kind;
    private Button send_message_confirm;
    private Button receive_message_confirm;
    private int select_message;

    @Override
    protected void onResume() {
        super.onResume();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    messageList.clear();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    int count = 0;
                    Integer number;
                    String sender, receiver, content, time;

                    while (count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(count);
                        number = object.getInt("number");
                        sender = object.getString("sender");
                        receiver = object.getString("receiver");
                        content = object.getString("content");
                        time = object.getString("time");

                        Message message = new Message(number, sender, receiver, content, time);
                        messageList.add(message);
                        count++;

                    }

                    if(check.equals("send"))
                        send_messageListAdapter.notifyDataSetChanged();
                    else
                        receive_messageListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MessageConfirmRequest messageConfirmRequest= new MessageConfirmRequest(loginID, check, listener);
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        queue.add(messageConfirmRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1: {
                messageList.remove(select_message);
                send_messageListAdapter.notifyDataSetChanged();
                receive_messageListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");

        message_kind = (TextView)findViewById(R.id.message_kind);
        send_message_confirm = (Button)findViewById(R.id.send_message_confirm);
        receive_message_confirm = (Button)findViewById(R.id.receive_message_confirm);

        messageListView = (ListView) findViewById(R.id.messageListView);
        messageList = new ArrayList<Message>();
        receive_messageListAdapter = new MessageListAdapter(getApplicationContext(), messageList, true);
        send_messageListAdapter = new MessageListAdapter(getApplicationContext(), messageList, false);
        messageListView.setAdapter(receive_messageListAdapter);

        send_message_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message_kind.setText("받는사람");
                check = "send";

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            messageList.clear();
                            messageListView.setAdapter(send_messageListAdapter);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("message");
                            int count = 0;
                            Integer number;
                            String sender, receiver, content, time;

                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                number = object.getInt("number");
                                sender = object.getString("sender");
                                receiver = object.getString("receiver");
                                content = object.getString("content");
                                time = object.getString("time");

                                Message message = new Message(number, sender, receiver, content, time);
                                messageList.add(message);
                                count++;

                                send_messageListAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                MessageConfirmRequest messageConfirmRequest= new MessageConfirmRequest(loginID, check, listener);
                RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
                queue.add(messageConfirmRequest);
            }
        });

        receive_message_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                message_kind.setText("보낸사람");
                check = "receive";

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            messageList.clear();
                            messageListView.setAdapter(receive_messageListAdapter);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("message");
                            int count = 0;
                            Integer number;
                            String sender, receiver, content, time;

                            while (count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(count);
                                number = object.getInt("number");
                                sender = object.getString("sender");
                                receiver = object.getString("receiver");
                                content = object.getString("content");
                                time = object.getString("time");

                                Message message = new Message(number, sender, receiver, content, time);
                                messageList.add(message);
                                count++;

                                receive_messageListAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                MessageConfirmRequest messageConfirmRequest= new MessageConfirmRequest(loginID, check, listener);
                RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
                queue.add(messageConfirmRequest);
            }
        });

        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(MessageActivity.this, MessageConfirmActivity.class);

                if(loginID.equals(messageList.get(i).getReceiver())) {
                    intent1.putExtra("userID", messageList.get(i).getSender());
                    intent1.putExtra("check", true);
                }
                else {
                    intent1.putExtra("userID", messageList.get(i).getReceiver());
                    intent1.putExtra("check",false);
                }

                intent1.putExtra("content", messageList.get(i).getContent())
                        .putExtra("number", messageList.get(i).getNumber())
                        .putExtra("loginID", loginID)
                        .putExtra("time", messageList.get(i).getTime());
                select_message = i;
                startActivityForResult(intent1, 0);
            }
        });


    }
}
