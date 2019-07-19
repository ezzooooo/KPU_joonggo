package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageSendActivity extends AppCompatActivity {

    private TextView receiver_TV;
    private Button message_send;
    private EditText content;
    private String sender, receiver;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        receiver = intent.getStringExtra("receiver");

        receiver_TV = (TextView)findViewById(R.id.message_receiver);

        receiver_TV.setText("받는사람 : " + receiver);

        message_send = (Button)findViewById(R.id.message_send);
        content = (EditText)findViewById(R.id.message_content);

        message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.android.volley.Response.Listener<String> listener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(MessageSendActivity.this, "메세지가 전송되었습니다.", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(MessageSendActivity.this, "메세지 전송을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                MessageSendRequest messageSendRequest = new MessageSendRequest(sender, receiver, content.getText().toString(), listener);
                RequestQueue requestQueue = Volley.newRequestQueue(MessageSendActivity.this);
                requestQueue.add(messageSendRequest);
            }
        });
    }
}
