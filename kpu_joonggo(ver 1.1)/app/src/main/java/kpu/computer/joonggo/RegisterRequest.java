package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userNickname, String userEmail, String userMajor, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userNickname", userNickname);
        parameters.put("userEmail", userEmail);
        parameters.put("userMajor", userMajor);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
