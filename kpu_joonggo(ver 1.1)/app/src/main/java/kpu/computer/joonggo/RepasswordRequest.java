package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RepasswordRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/Repassword.php";
    private Map<String, String> parameters;

    public RepasswordRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
