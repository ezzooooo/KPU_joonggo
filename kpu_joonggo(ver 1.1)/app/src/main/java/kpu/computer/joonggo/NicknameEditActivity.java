package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class NicknameEditActivity extends AppCompatActivity {

    private EditText et_Nickname;
    private Button validateButton;
    private Button commitButton;
    private AlertDialog dialog;
    private Boolean nickValidate = false;
    private String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_edit);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");

        et_Nickname = (EditText) findViewById(R.id.edit_nicknameText);
        validateButton = (Button) findViewById(R.id.edit_validateButton1);
        commitButton = (Button) findViewById(R.id.nickname_commitButton);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nickValidate)
                    return;

                String userNickname = et_Nickname.getText().toString();
                if (userNickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NicknameEditActivity.this);
                    dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    nickValidate = false;
                    return;
                } else if (userNickname.contains(" ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NicknameEditActivity.this);
                    dialog = builder.setMessage("닉네임에 공백이 들어갈 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    nickValidate = false;
                    return;
                } else if (userNickname.matches(".*[!#$%&'()*+,-./:;<=>?@^_`{|}~].*")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(NicknameEditActivity.this);
                    dialog = builder1.setMessage("닉네임에 특수문자가 들어갈 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    nickValidate = false;
                    return;
                } else if (userNickname.length() < 3) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(NicknameEditActivity.this);
                    dialog = builder1.setMessage("닉네임이 너무 짧습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    nickValidate = false;
                    return;
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NicknameEditActivity.this);
                                    dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    nickValidate = true;
                                    validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NicknameEditActivity.this);
                                    dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                    nickValidate = false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    NicknameValidateRequest nicknameValidateRequest = new NicknameValidateRequest(userNickname, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(NicknameEditActivity.this);
                    queue.add(nicknameValidateRequest);
                }
            }
        });

        et_Nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nickValidate = false;
                validateButton.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nickValidate) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(NicknameEditActivity.this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(NicknameEditActivity.this, "닉네임 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    NicknameEditRequest nicknameEditRequest = new NicknameEditRequest(loginID, et_Nickname.getText().toString(), listener);
                    RequestQueue requestQueue = Volley.newRequestQueue(NicknameEditActivity.this);
                    requestQueue.add(nicknameEditRequest);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NicknameEditActivity.this);
                    dialog = builder.setMessage("중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });
    }
}
