package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductSaleFinishRequest extends StringRequest {
    final static private String URL = "http://114.204.73.214/ProductSaleFinish.php";
    private Map<String, String> parameters;

    public ProductSaleFinishRequest(Integer productNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("productNumber", String.valueOf(productNumber));
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
