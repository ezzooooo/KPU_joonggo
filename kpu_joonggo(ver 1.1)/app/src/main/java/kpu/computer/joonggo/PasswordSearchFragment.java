package kpu.computer.joonggo;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordSearchFragment extends Fragment {

    private String loginID;
    private String userEmail;
    TextView countText;
    private Handler handler;
    private int mValue = 180;
    private int minute;
    private int second;
    private Timer mTimer;
    private String emailCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_search_fragment_, container, false);
        final Button EnterButton = (Button)view.findViewById(R.id.EnterButton);
        final Button Enter2Button = (Button)view.findViewById(R.id.Enter2Button);
        final Button RetryButton = (Button)view.findViewById(R.id.RetryButton);
        final EditText IDText = (EditText)view.findViewById(R.id.idText);
        final EditText EmailText = (EditText)view.findViewById(R.id.emailText);
        //final TextView countText = (TextView)view.findViewById(R.id.countText);
        final EditText numberEdit = (EditText)view.findViewById(R.id.numberEdit);
        countText = (TextView)view.findViewById(R.id.countText);

        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailCode = new EmailCode().getEmailCode();
                loginID = IDText.getText().toString();
                userEmail = EmailText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) { // 여기에 카운트다운 넣어야됨 규영

                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                };

                                EmailRequest emailRequest = new EmailRequest(userEmail, emailCode, listener);
                                RequestQueue emailqueue = Volley.newRequestQueue(getActivity());
                                emailqueue.add(emailRequest);

                                numberEdit.setVisibility(View.VISIBLE);
                                countText.setVisibility(View.VISIBLE);
                                Enter2Button.setVisibility(View.VISIBLE);
                                RetryButton.setVisibility(View.VISIBLE);

                                mTimer = new Timer(true);
                                handler = new Handler();
                                mTimer.schedule(
                                        new TimerTask() {
                                            @Override
                                            public void run() {
                                                handler.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        minute = mValue / 60;
                                                        second = mValue % 60;
                                                        if (mValue == 0) {
                                                            countText.setText("03:00");
                                                            mTimer.cancel();
                                                            mValue=180;
                                                            numberEdit.setEnabled(false);
                                                            Enter2Button.setEnabled(false);
                                                        } else if (second <10){
                                                            countText.setText("0"+Integer.toString(minute) + ":0" + Integer.toString(second));
                                                        } else
                                                            countText.setText("0"+Integer.toString(minute) + ":" + Integer.toString(second));
                                                        mValue--;
                                                    }
                                                });
                                            }
                                        }, 1000, 1000
                                );
                            }
                            else {
                                Toast.makeText(getActivity(), "아이디, 이메일 주소를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                PasswordSearchRequest passwordSearchRequest = new PasswordSearchRequest(loginID, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(passwordSearchRequest);

            }
        });

        Enter2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberEdit.getText().toString().equals(emailCode)) {
                    Intent intent = new Intent(PasswordSearchFragment.this.getActivity(),RepasswordActivity.class);
                    intent.putExtra("loginID", loginID);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "인증번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailCode = new EmailCode().getEmailCode();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };

                EmailRequest emailRequest = new EmailRequest(userEmail, emailCode, listener);
                RequestQueue emailqueue = Volley.newRequestQueue(getActivity());
                emailqueue.add(emailRequest);

                mTimer.cancel();

                countText.setText("03:00");
                mValue = 180;
                mTimer = new Timer(true);
                numberEdit.setEnabled(true);
                handler = new Handler();
                mTimer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        minute = mValue / 60;
                                        second = mValue % 60;
                                        if (mValue == 0) {
                                            countText.setText("03:00");
                                            mTimer.cancel();
                                            mValue=180;
                                            numberEdit.setEnabled(false);
                                            Enter2Button.setEnabled(false);
                                        } else if (second < 10){
                                            countText.setText("0"+Integer.toString(minute) + ":0" + Integer.toString(second));
                                        } else
                                            countText.setText("0"+Integer.toString(minute) + ":" + Integer.toString(second));
                                        mValue--;

                                    }
                                });
                            }
                        }, 1000, 1000
                );
            }
        });

        return view;
    }
}
