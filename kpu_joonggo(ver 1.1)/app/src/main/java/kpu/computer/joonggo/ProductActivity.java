package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ListView productListView;
    private ProductListAdapter productListAdapter;
    private List<Product> productList;
    private TextView what_product;

    private String loginID;
    private int product_kind;
    private int select_product;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        what_product = (TextView) findViewById(R.id.what_product);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");
        product_kind = intent.getIntExtra("product_kind", 0);

        productListView = (ListView) findViewById(R.id.likeProductListView);
        productList = new ArrayList<Product>();
        productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
        productListView.setAdapter(productListAdapter);

        if (product_kind == 0) {

            what_product.setText("내 상품");

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("product");
                        int count = 0;
                        Integer productNumber, productPrice;
                        String productSeller, productExp, productCategory, productName = null, productStat, imagePath1, imagePath2, imagePath3;

                        while (count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(count);
                            productNumber = object.getInt("productNumber");
                            productSeller = object.getString("productSeller");
                            productName = object.getString("productName");
                            productExp = object.getString("productExp");
                            productCategory = object.getString("productCategory");
                            productPrice = object.getInt("productPrice");
                            productStat = object.getString("productStat");
                            imagePath1 = object.getString("productImage1");
                            imagePath2 = object.getString("productImage2");
                            imagePath3 = object.getString("productImage3");

                            Product product = new Product(productNumber, productSeller, productName, productExp, productCategory, productPrice, productStat, imagePath1, imagePath2, imagePath3);
                            productList.add(product);
                            count++;

                            productListAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            MyProductSendRequest myProductSendRequest = new MyProductSendRequest(loginID, listener);
            RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
            queue.add(myProductSendRequest);
        } else if (product_kind == 1) {

            what_product.setText("찜한 상품");

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("product");
                        int count = 0;
                        Integer productNumber, productPrice;
                        String productSeller, productExp, productCategory, productName = null, productStat, imagePath1, imagePath2, imagePath3;

                        while (count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(count);
                            productNumber = object.getInt("productNumber");
                            productSeller = object.getString("productSeller");
                            productName = object.getString("productName");
                            productExp = object.getString("productExp");
                            productCategory = object.getString("productCategory");
                            productPrice = object.getInt("productPrice");
                            productStat = object.getString("productStat");
                            imagePath1 = object.getString("productImage1");
                            imagePath2 = object.getString("productImage2");
                            imagePath3 = object.getString("productImage3");

                            Product product = new Product(productNumber, productSeller, productName, productExp, productCategory, productPrice, productStat, imagePath1, imagePath2, imagePath3);
                            productList.add(product);
                            count++;

                            productListAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LikeProductSendRequest likeProductSendRequest = new LikeProductSendRequest(loginID, listener);
            RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
            queue.add(likeProductSendRequest);
        } else if (product_kind == 2) {
            what_product.setText("무료 상품");

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("product");
                        int count = 0;
                        Integer productNumber, productPrice;
                        String productSeller, productExp, productCategory, productName = null, productStat, imagePath1, imagePath2, imagePath3;

                        while (count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(count);
                            productNumber = object.getInt("productNumber");
                            productSeller = object.getString("productSeller");
                            productName = object.getString("productName");
                            productExp = object.getString("productExp");
                            productCategory = object.getString("productCategory");
                            productPrice = object.getInt("productPrice");
                            productStat = object.getString("productStat");
                            imagePath1 = object.getString("productImage1");
                            imagePath2 = object.getString("productImage2");
                            imagePath3 = object.getString("productImage3");

                            Product product = new Product(productNumber, productSeller, productName, productExp, productCategory, productPrice, productStat, imagePath1, imagePath2, imagePath3);
                            productList.add(product);
                            count++;

                            productListAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            FreeProductRequest freeProductRequest = new FreeProductRequest(listener);
            RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
            queue.add(freeProductRequest);
        }

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(ProductActivity.this, PickProductActivity.class);
                intent1.putExtra("productNumber", productList.get(i).getProductNumber())
                        .putExtra("productName", productList.get(i).getName())
                        .putExtra("productPrice", productList.get(i).getPrice())
                        .putExtra("productStat", productList.get(i).getStat())
                        .putExtra("productSeller", productList.get(i).getSeller())
                        .putExtra("productExp", productList.get(i).getExp())
                        .putExtra("productCategory", productList.get(i).getCategory())
                        .putExtra("productImage1", productList.get(i).getImagepath1())
                        .putExtra("productImage2", productList.get(i).getImagepath2())
                        .putExtra("productImage3", productList.get(i).getImagepath3())
                        .putExtra("loginID", loginID);
                select_product = i;
                startActivityForResult(intent1, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 1: {
                productList.remove(select_product);
                productListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}



