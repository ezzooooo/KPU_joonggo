package kpu.computer.joonggo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID="";
    private String userPassword="";
    private String userMajor="";
    private String userEmail="";
    private String userNickname="";
    private AlertDialog dialog;
    private boolean validate = false;
    private boolean Nickvalidate = false;
    private boolean Passvalidate = false;
    private boolean space = false;
    private boolean Passspace = false;
    private boolean another = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner) findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final EditText idText = (EditText) findViewById(R.id.idText);
        idText.setPrivateImeOptions("defaultInputmode=english;");
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordText1 = (EditText) findViewById(R.id.passwordText1); //비밀번호확인 902
        final EditText nicknameText = (EditText)findViewById(R.id.nicknameText); // 닉네임 902
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        emailText.setPrivateImeOptions("defaultInputmode=english;");
        final EditText adressText = (EditText)findViewById(R.id.emailadressText);
        adressText.setPrivateImeOptions("defaultInputmode:english;");

        Spinner spinner2 = (Spinner)findViewById(R.id.emailSpinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this,R.array.email,android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adressText.setText(adapterView.getItemAtPosition(i).toString());
                String adressEmail = adressText.getText().toString();
                if (adressEmail.equals("직접입력")){
                    adressText.setText("");
                    adressText.setEnabled(true);
                } else
                    adressText.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate)
                    return;

                String userID = idText.getText().toString();
                if(userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                }// 아이디 공백 확인
                else if (userID.contains(" ")) {
                    space=true;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("아이디에 공백이 들어갈 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                } else if (userID.matches(".*[ㄱ-ㅎ기-힣].*")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("아이디에 한글이 들어갈 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                }else if (userID.matches(".*[!#$%&'()*+,-./:;<=>?@^_`{|}~].*")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("아이디에 특수문자가 들어갈 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                }else if (userID.length()<4){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("아이디가 너무 짧습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    validate = false;
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                validate = true;
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                                validate = false;
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        // 닉네임 부분
        final Button validateButton1 = (Button) findViewById(R.id.validateButton1);
        validateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Nickvalidate)
                    return;

                String userNickname = nicknameText.getText().toString();
                if (userNickname.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임은 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    Nickvalidate = false;
                    return;
                } else if (userNickname.contains(" ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임에 공백이 들어갈 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    Nickvalidate = false;
                    return;
                } else if (userNickname.matches(".*[!#$%&'()*+,-./:;<=>?@^_`{|}~].*")) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("닉네임에 특수문자가 들어갈 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    Nickvalidate = false;
                    return;
                } else if (userNickname.length() < 3) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder1.setMessage("닉네임이 너무 짧습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    Nickvalidate = false;
                    return;
                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("사용할 수 있는 닉네임입니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Nickvalidate = true;
                                    validateButton1.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    dialog = builder.setMessage("사용할 수 없는 닉네임입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Nickvalidate = false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    NicknameValidateRequest nicknameValidateRequest = new NicknameValidateRequest(userNickname, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(nicknameValidateRequest);
                }
            }
        });

        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validate = false;
                validateButton.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nicknameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Nickvalidate = false;
                validateButton1.setBackgroundColor(getResources().getColor(R.color.colorWarning));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // 비밀번호 확인
        passwordText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (passwordText.getText().toString().equals(passwordText1.getText().toString())){

                } else {

            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (passwordText1.getText().toString().equals(passwordText.getText().toString())){

                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = spinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString() + "@" + adressText.getText().toString();
                String userNickname = nicknameText.getText().toString(); // 닉네임 받아오기 902
                String userPassword1 = passwordText1.getText().toString(); // 비밀번호확인 받아오기 902
                String userAdrees = adressText.getText().toString();

                if (!validate || !Nickvalidate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if (userPassword.length() < 6) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 너무 짧습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else if (userPassword.contains(" ") || userPassword1.contains(" ")) {
                    AlertDialog.Builder buil = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = buil.setMessage("비밀번호에 공백이 있을수 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else if (!(userPassword.equals(userPassword1))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else if (userEmail.matches(".*[ㄱ-ㅎ기-힣].*") || userEmail.contains(" ") ||
                        userAdrees.contains(" ") || userAdrees.matches(".*[ㄱ-ㅎ기-힣].*")) {
                    AlertDialog.Builder buil = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = buil.setMessage("이메일 형식이 잘 못되었습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }

                // 기존의 userGender.equals("") 삭제하고 phone 하고 nickname, password확인 추가 902
                else if (userID.equals("") || userPassword.equals("") || userMajor.equals("") || userEmail.equals("") || userNickname.equals("") || userPassword1.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(RegisterActivity.this, "회원가입을 축하합니다 ^_^", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userNickname, userEmail, userMajor, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
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
