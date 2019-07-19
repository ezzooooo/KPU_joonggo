package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageDeleteRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/MessageDelete.php";
    private Map<String, String> parameters;

    public MessageDeleteRequest(Integer number, String kind, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("number", String.valueOf(number));
        parameters.put("kind", kind);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
