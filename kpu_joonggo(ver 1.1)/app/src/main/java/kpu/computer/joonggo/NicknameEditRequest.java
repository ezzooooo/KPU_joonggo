package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NicknameEditRequest extends StringRequest {

    final static private String URL = "http://114.204.73.214/NicknameEdit.php";
    private Map<String, String> parameters;

    public NicknameEditRequest(String userID, String userNickname, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userNickname", userNickname);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
