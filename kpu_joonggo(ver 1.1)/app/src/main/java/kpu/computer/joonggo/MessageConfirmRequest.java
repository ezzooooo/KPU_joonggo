package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageConfirmRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/MessageConfirm.php";
    private Map<String, String> parameters;

    public MessageConfirmRequest(String userID, String check, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("check", check);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
