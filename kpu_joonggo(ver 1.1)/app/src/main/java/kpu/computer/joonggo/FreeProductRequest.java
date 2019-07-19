package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class FreeProductRequest extends StringRequest {
    final static private String URL = "http://114.204.73.214/FreeProduct.php";

    public FreeProductRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }
}
