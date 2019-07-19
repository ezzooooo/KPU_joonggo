package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

public class EmailEditActivity extends AppCompatActivity {

    private EditText emailText;
    private Button commitButton;
    private AlertDialog dialog;
    private ArrayAdapter adapter;
    private Spinner spinner;
    private String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_edit);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");
        emailText = (EditText)findViewById(R.id.emailText);
        final EditText adressText = (EditText)findViewById(R.id.emailadressText);
        commitButton = (Button) findViewById(R.id.email_commitButton);

        spinner = (Spinner)findViewById(R.id.emailSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.email,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailText.getText().toString();
                String userAdrees = adressText.getText().toString();
                if (userEmail.matches(".*[ㄱ-ㅎ기-힣].*") || userEmail.contains(" ") ||
                        userAdrees.contains(" ") || userAdrees.matches(".*[ㄱ-ㅎ기-힣].*")) {
                    AlertDialog.Builder buil = new AlertDialog.Builder(EmailEditActivity.this);
                    dialog = buil.setMessage("이메일 형식이 잘 못되었습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Toast.makeText(EmailEditActivity.this, "이메일이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(EmailEditActivity.this, "이메일 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    EmailEditRequest emailEditRequest = new EmailEditRequest(loginID, emailText.getText().toString() + "@" + adressText.getText().toString(), listener);
                    RequestQueue requestQueue = Volley.newRequestQueue(EmailEditActivity.this);
                    requestQueue.add(emailEditRequest);
                }
            }
        });
    }
}
