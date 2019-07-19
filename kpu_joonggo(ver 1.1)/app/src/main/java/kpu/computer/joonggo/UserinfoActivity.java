package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserinfoActivity extends AppCompatActivity {

    private TextView TV_info_ID;
    private TextView TV_info_Nickname;
    private TextView TV_info_Email;
    private TextView TV_info_Major;
    private TextView TV_info_Repassword;
    private String loginID;

    @Override
    protected void onResume() {
        super.onResume();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    TV_info_ID.setText(loginID);
                    TV_info_Nickname.setText(jsonObject.getString("userNickname"));
                    TV_info_Email.setText(jsonObject.getString("userEmail"));
                    TV_info_Major.setText(jsonObject.getString("userMajor"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UserinfoRequest userinfoRequest = new UserinfoRequest(loginID, listener);
        RequestQueue queue = Volley.newRequestQueue(UserinfoActivity.this);
        queue.add(userinfoRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        Intent get_intent = getIntent();
        loginID = get_intent.getStringExtra("loginID");

        TV_info_ID = (TextView) findViewById(R.id.info_id);
        TV_info_Nickname = (TextView) findViewById(R.id.info_nickname);
        TV_info_Email = (TextView) findViewById(R.id.info_email);
        TV_info_Major = (TextView) findViewById(R.id.info_major);
        TV_info_Repassword = (TextView) findViewById(R.id.info_repassword);

        TV_info_Nickname.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(UserinfoActivity.this, NicknameEditActivity.class);
            @Override
            public void onClick(View view) {
                intent.putExtra("loginID", loginID);
                startActivity(intent);
            }
        });

        TV_info_Email.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(UserinfoActivity.this, EmailEditActivity.class);
            @Override
            public void onClick(View view) {
                intent.putExtra("loginID", loginID);
                startActivity(intent);
            }
        });

        TV_info_Major.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(UserinfoActivity.this, MajorEditActivity.class);
            @Override
            public void onClick(View view) {
                intent.putExtra("loginID", loginID);
                startActivity(intent);
            }
        });

        TV_info_Repassword.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent(UserinfoActivity.this, RepasswordActivity.class);
            @Override
            public void onClick(View view) {
                intent.putExtra("loginID", loginID);
                startActivity(intent);
            }
        });
    }
}
