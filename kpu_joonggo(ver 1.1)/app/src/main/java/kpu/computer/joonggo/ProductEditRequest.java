package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductEditRequest extends StringRequest{

    final static private String URL = "http://114.204.73.214/ProductEdit.php";
    private Map<String, String> parameters;

    public ProductEditRequest(Integer productNumber, String productName, String productExp,
                              String productCategory, String productPrice, String productStat,
                              Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("productNumber", String.valueOf(productNumber));
        parameters.put("productName", productName);
        parameters.put("productExp", productExp);
        parameters.put("productCategory", productCategory);
        parameters.put("productPrice", productPrice);
        parameters.put("productStat", productStat);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
