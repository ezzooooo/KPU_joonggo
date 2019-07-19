package kpu.computer.joonggo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final EditText questions = (EditText) findViewById(R.id.questions);
        Button question_commit = (Button) findViewById(R.id.question_commit);

        question_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(QuestionActivity.this, "문의가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(QuestionActivity.this, "문의 전송을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                QuestionCommitRequest questionCommitRequest = new QuestionCommitRequest(questions.getText().toString(), listener);
                RequestQueue requestQueue = Volley.newRequestQueue(QuestionActivity.this);
                requestQueue.add(questionCommitRequest);
            }
        });
    }
}
