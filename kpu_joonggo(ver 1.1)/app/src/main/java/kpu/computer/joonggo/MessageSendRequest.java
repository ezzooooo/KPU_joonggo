package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageSendRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/MessageSend.php";
    private Map<String, String> parameters;

    public MessageSendRequest(String sender, String receiver, String content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("sender", sender);
        parameters.put("receiver", receiver);
        parameters.put("content", content);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
