package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RepasswordActivity extends AppCompatActivity {

    private String loginID;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repassword);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");

        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordText1 = (EditText) findViewById(R.id.passwordText1); //비밀번호확인 902
        final Button EnterButton = (Button)findViewById(R.id.EnterButton);

        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPassword = passwordText.getText().toString();
                String Repassword = passwordText1.getText().toString();

                if (userPassword.equals("") || Repassword.equals("")){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if (userPassword.length() < 6) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RepasswordActivity.this);
                    dialog = builder.setMessage("비밀번호가 너무 짧습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else if (userPassword.contains(" ") || Repassword.contains(" ")) {
                    AlertDialog.Builder buil = new AlertDialog.Builder(RepasswordActivity.this);
                    dialog = buil.setMessage("비밀번호에 공백이 있을수 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else if (!(userPassword.equals(Repassword))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RepasswordActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                else if (!(userPassword.equals("")) && !(Repassword.equals(""))) {
                    if ( userPassword.equals(passwordText1.getText().toString()) && Repassword.equals(passwordText.getText().toString())) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success) {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                                finish();
                            }
                        };
                        RepasswordRequest repasswordRequest = new RepasswordRequest(loginID, userPassword, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RepasswordActivity.this);
                        queue.add(repasswordRequest);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
