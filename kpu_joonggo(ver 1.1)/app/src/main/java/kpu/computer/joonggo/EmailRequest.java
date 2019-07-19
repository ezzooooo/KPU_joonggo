package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EmailRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/SendMail.php";
    private Map<String, String> parameters;

    public EmailRequest(String userEmail, String emailCode, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userEmail", userEmail);
        parameters.put("emailCode", emailCode);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
