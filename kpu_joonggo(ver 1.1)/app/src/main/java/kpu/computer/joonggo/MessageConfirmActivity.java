package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageConfirmActivity extends AppCompatActivity {

    private TextView content;
    private TextView time;
    private TextView userID;
    private Button resend, delete;
    private Integer number;
    private String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_confirm);

        content = (TextView)findViewById(R.id.message_confirm_content);
        time = (TextView)findViewById(R.id.message_confirm_time);
        userID = (TextView)findViewById(R.id.message_confirm_userID);
        resend = (Button) findViewById(R.id.message_resend_button);
        delete = (Button) findViewById(R.id.message_delete_button);

        final Intent intent = getIntent();
        final Boolean check = intent.getBooleanExtra("check", false);
        number = intent.getIntExtra("number", 0);
        loginID = intent.getStringExtra("loginID");

        if(check) {
            userID.setText("보낸사람 : " + intent.getStringExtra("userID"));
            time.setText("받은시간 : " + intent.getStringExtra("time"));
        } else {
            userID.setText("받은사람 : " + intent.getStringExtra("userID"));
            time.setText("보낸시간 : " + intent.getStringExtra("time"));
            resend.setVisibility(View.GONE);
        }

        content.setText("내용 : " + intent.getStringExtra("content"));

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MessageConfirmActivity.this, MessageSendActivity.class);
                intent1.putExtra("sender", loginID);
                intent1.putExtra("receiver", intent.getStringExtra("userID"));
                startActivity(intent1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageDeleteRequest messageDeleteRequest;
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(MessageConfirmActivity.this, "메세지가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                setResult(1);
                                finish();
                            } else {
                                Toast.makeText(MessageConfirmActivity.this, "메세지 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if(check)
                    messageDeleteRequest = new MessageDeleteRequest(number, "receive", listener);
                else
                    messageDeleteRequest = new MessageDeleteRequest(number, "send", listener);

                RequestQueue requestQueue = Volley.newRequestQueue(MessageConfirmActivity.this);
                requestQueue.add(messageDeleteRequest);
            }
        });

    }
}
