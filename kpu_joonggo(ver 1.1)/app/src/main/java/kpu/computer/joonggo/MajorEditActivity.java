package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MajorEditActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Button commitButton;
    private Spinner spinner;
    private String loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major_edit);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");

        spinner = (Spinner) findViewById(R.id.edit_majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        commitButton = (Button) findViewById(R.id.major_commitButton);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(MajorEditActivity.this, "전공이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(MajorEditActivity.this, "전공 변경을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                MajorEditRequest majorEditRequest = new MajorEditRequest(loginID, spinner.getSelectedItem().toString(), listener);
                RequestQueue requestQueue = Volley.newRequestQueue(MajorEditActivity.this);
                requestQueue.add(majorEditRequest);
            }
        });

    }
}
