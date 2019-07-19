package kpu.computer.joonggo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductaddRequest extends StringRequest{

    final static private String URL = "http://114.204.73.214/ProductAdd.php";
    private Map<String, String> parameters;

    public ProductaddRequest(String productSeller, String productName, String productExp,
                             String productCategory, String productPrice, String productStat,
                             String productImage1, String productImage2, String productImage3,
                             Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        parameters.put("productSeller", productSeller);
        parameters.put("productName", productName);
        parameters.put("productExp", productExp);
        parameters.put("productCategory", productCategory);
        parameters.put("productPrice", productPrice);
        parameters.put("productStat", productStat);
        parameters.put("productImage1", productImage1);
        parameters.put("productImage2", productImage2);
        parameters.put("productImage3", productImage3);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
