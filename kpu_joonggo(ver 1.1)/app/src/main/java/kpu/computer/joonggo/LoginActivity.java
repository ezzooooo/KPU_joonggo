package kpu.computer.joonggo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private CheckBox id_save;
    private CheckBox auto_login;
    private boolean is_logout;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Intent intent = getIntent();
        is_logout = intent.getBooleanExtra("is_logout", false);

        SharedPreferences setting;
        setting = getSharedPreferences("setting", 0);

        final SharedPreferences.Editor editor = setting.edit();

        id_save = (CheckBox) findViewById(R.id.id_save_Checkbox);
        auto_login = (CheckBox) findViewById(R.id.auto_login_Checkbox);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        TextView idSearchButton = (TextView)findViewById(R.id.IDSearchButton);
        idSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent idSearchIntent = new Intent(LoginActivity.this,IDSearchActivity.class);
                LoginActivity.this.startActivity(idSearchIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(auto_login.isChecked()) {
                    id_save.setEnabled(false);
                    if (!id_save.isChecked()) {
                        id_save.setChecked(true);
                    }
                } else {
                    id_save.setEnabled(true);
                }

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {

                                String userEmail = jsonResponse.getString("userEmail");
                                String userNickname = jsonResponse.getString("userNickname");

                                if(auto_login.isChecked()) {
                                    editor.putString("loginID", userID);
                                    editor.putString("loginPassword", userPassword);
                                    editor.putBoolean("auto_Login", true);
                                    editor.commit();
                                }
                                if(id_save.isChecked()) {
                                    editor.putString("userID", userID);
                                    editor.putBoolean("id_Save", true);
                                    editor.commit();
                                }
                                if (!id_save.isChecked()){
                                    editor.remove("userID");
                                    editor.remove("id_Save");
                                    editor.commit();
                                }
                                if (!auto_login.isChecked()){
                                    editor.remove("loginID");
                                    editor.remove("loginPassword");
                                    editor.remove("auto_Login");
                                    editor.commit();
                                }
                                Toast.makeText(getApplicationContext(),userNickname+"님 환영합니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                                intent.putExtra("loginID", idText.getText().toString());
                                intent.putExtra("userEmail", userEmail);
                                intent.putExtra("userNickname", userNickname);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    dialog = builder.setMessage("계정을 다시 확인하세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else if (userPassword.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });

        if(setting.getBoolean("id_Save", false)) {
            idText.setText(setting.getString("userID",""));
            id_save.setChecked(true);
        }

        if(setting.getBoolean("auto_Login",false)) {
            idText.setText(setting.getString("loginID",""));
            passwordText.setText(setting.getString("loginPassword",""));
            auto_login.setChecked(true);

            if(!is_logout)
                loginButton.callOnClick();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
