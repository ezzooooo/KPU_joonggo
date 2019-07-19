package kpu.computer.joonggo;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class IDSearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.idsearch_fragment,container,false);
        final TextView result = (TextView)view.findViewById(R.id.idResult);
        Button EnterButton = (Button)view.findViewById(R.id.EnterButton);
        final EditText emailText = (EditText)view.findViewById(R.id.emailText);

        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userID = jsonResponse.getString("userID");
                                result.setText("확인된 아이디는 " + userID + " 입니다.");
                            }
                            else {
                                result.setText("확인된 아이디가 없습니다.");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                IDSearchRequest idSearchRequest = new IDSearchRequest(userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(idSearchRequest);
            }
        });

        return view;
    }

}
