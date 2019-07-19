package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {

    private ListView productListView;
    private ProductListAdapter productListAdapter;
    private ProductListAdapter search_productListAdapter;
    private ProductListAdapter price_search_productListAdapter;
    private List<Product> productList;
    private List<Product> search_productList;
    private List<Product> price_search_productList;
    private String searchName;
    private String loginID;
    int setting_sort=1, setting_stat=1, setting_minprice, setting_maxprice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        final Intent intent = getIntent();
        searchName = intent.getStringExtra("searchName");
        loginID = intent.getStringExtra("loginID");

        productListView = (ListView) findViewById(R.id.search_product_listview);
        productList = new ArrayList<Product>();
        productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
        productListView.setAdapter(productListAdapter);

        search_productList = new ArrayList<Product>();
        price_search_productList = new ArrayList<Product>();

        Button search_setting = (Button) findViewById(R.id.search_setting);

        search_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SearchProductActivity.this, SearchSettingActivity.class);
                intent1.putExtra("setting_sort", setting_sort);
                intent1.putExtra("setting_stat", setting_stat);
                startActivityForResult(intent1, 1);
            }
        });

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

            ProductSearchRequest productSearchRequest = new ProductSearchRequest(searchName, listener);
            RequestQueue queue = Volley.newRequestQueue(SearchProductActivity.this);
            queue.add(productSearchRequest);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(SearchProductActivity.this, PickProductActivity.class);
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
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0) {
            return;
        }

        search_productList.clear();
        price_search_productList.clear();

        int i = 0;

        setting_sort = data.getIntExtra("setting_sort", -1);
        setting_stat = data.getIntExtra("setting_stat", -1);

        if(resultCode == 1) {

            switch (setting_sort) {
                case 1:
                    Comparator<Product> newest = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getProductNumber() - t1.getProductNumber()) ;
                        }
                    } ;
                    Collections.sort(productList, newest);
                    productListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Comparator<Product> lowprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getPrice() - t1.getPrice()) ;
                        }
                    } ;
                    Collections.sort(productList, lowprice);
                    productListAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    Comparator<Product> highprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (t1.getPrice() - product.getPrice()) ;
                        }
                    } ;
                    Collections.sort(productList, highprice);
                    productListAdapter.notifyDataSetChanged();
                    break;
            }

            switch (setting_stat) {
                case 1:
                    productListView.setAdapter(productListAdapter);
                    break;
                case 2:
                    while(i < productListAdapter.getCount()) {
                        if(productList.get(i).getStat().equals("새상품")) {
                            search_productList.add(productList.get(i));
                        }
                        i++;
                    }
                    search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);
                    productListView.setAdapter(search_productListAdapter);
                    break;
                case 3:
                    while(i < productListAdapter.getCount()) {
                        if(productList.get(i).getStat().equals("거의 새것")) {
                            search_productList.add(productList.get(i));
                        }
                        i++;
                    }
                    search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);
                    productListView.setAdapter(search_productListAdapter);
                    break;
                case 4:
                    while(i < productListAdapter.getCount()) {
                        if(productList.get(i).getStat().equals("중고")) {
                            search_productList.add(productList.get(i));
                        }
                        i++;
                    }
                    search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);
                    productListView.setAdapter(search_productListAdapter);
                    break;
            }

        }

        else if (resultCode == 2) {
            setting_maxprice = Integer.parseInt(data.getStringExtra("setting_maxprice"));

            while(i < productListAdapter.getCount()) {
                if(productList.get(i).getPrice() <= setting_maxprice) {
                    search_productList.add(productList.get(i));
                }
                i++;
            }

            i=0;

            search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);

                switch (setting_sort) {
                    case 1:
                        Comparator<Product> newest = new Comparator<Product>() {
                            @Override
                            public int compare(Product product, Product t1) {
                                return (product.getProductNumber() - t1.getProductNumber()) ;
                            }
                        } ;
                        Collections.sort(search_productList, newest);
                        search_productListAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        Comparator<Product> lowprice = new Comparator<Product>() {
                            @Override
                            public int compare(Product product, Product t1) {
                                return (product.getPrice() - t1.getPrice()) ;
                            }
                        } ;
                        Collections.sort(search_productList, lowprice);
                        search_productListAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        Comparator<Product> highprice = new Comparator<Product>() {
                            @Override
                            public int compare(Product product, Product t1) {
                                return (t1.getPrice() - product.getPrice()) ;
                            }
                        } ;
                        Collections.sort(search_productList, highprice);
                        search_productListAdapter.notifyDataSetChanged();
                        break;
                }

                switch (setting_stat) {
                    case 1:
                        productListView.setAdapter(search_productListAdapter);
                        break;
                    case 2:
                        while(i < search_productListAdapter.getCount()) {
                            if(search_productList.get(i).getStat().equals("새상품")) {
                                price_search_productList.add(search_productList.get(i));
                            }
                            i++;
                        }
                        price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                        productListView.setAdapter(price_search_productListAdapter);
                        break;
                    case 3:
                        while(i < search_productListAdapter.getCount()) {
                            if(search_productList.get(i).getStat().equals("거의 새것")) {
                                price_search_productList.add(search_productList.get(i));
                            }
                            i++;
                        }
                        price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                        productListView.setAdapter(price_search_productListAdapter);
                        break;
                    case 4:
                        while(i < search_productListAdapter.getCount()) {
                            if(search_productList.get(i).getStat().equals("중고")) {
                                price_search_productList.add(search_productList.get(i));
                            }
                            i++;
                        }
                        price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                        productListView.setAdapter(price_search_productListAdapter);
                        break;
                }
        } else if (resultCode == 3) {
            setting_sort = data.getIntExtra("setting_sort", -1);
            setting_stat = data.getIntExtra("setting_stat", -1);
            setting_minprice = Integer.parseInt(data.getStringExtra("setting_minprice"));

            while(i < productListAdapter.getCount()) {
                if(productList.get(i).getPrice() >= setting_minprice) {
                    search_productList.add(productList.get(i));
                }
                i++;
            }

            i=0;

            search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);

            switch (setting_sort) {
                case 1:
                    Comparator<Product> newest = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getProductNumber() - t1.getProductNumber()) ;
                        }
                    } ;
                    Collections.sort(search_productList, newest);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Comparator<Product> lowprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getPrice() - t1.getPrice()) ;
                        }
                    } ;
                    Collections.sort(search_productList, lowprice);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    Comparator<Product> highprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (t1.getPrice() - product.getPrice()) ;
                        }
                    } ;
                    Collections.sort(search_productList, highprice);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
            }

            switch (setting_stat) {
                case 1:
                    productListView.setAdapter(search_productListAdapter);
                    break;
                case 2:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("새상품")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
                case 3:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("거의 새것")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
                case 4:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("중고")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
            }
        }
        else if (resultCode == 4) {
            setting_sort = data.getIntExtra("setting_sort", -1);
            setting_stat = data.getIntExtra("setting_stat", -1);
            setting_maxprice = Integer.parseInt(data.getStringExtra("setting_maxprice"));
            setting_minprice = Integer.parseInt(data.getStringExtra("setting_minprice"));

            while(i < productListAdapter.getCount()) {
                if(productList.get(i).getPrice() >= setting_minprice && productList.get(i).getPrice() <= setting_maxprice) {
                    search_productList.add(productList.get(i));
                }
                i++;
            }

            i=0;

            search_productListAdapter = new ProductListAdapter(getApplicationContext(), search_productList);

            switch (setting_sort) {
                case 1:
                    Comparator<Product> newest = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getProductNumber() - t1.getProductNumber()) ;
                        }
                    } ;
                    Collections.sort(search_productList, newest);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Comparator<Product> lowprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (product.getPrice() - t1.getPrice()) ;
                        }
                    } ;
                    Collections.sort(search_productList, lowprice);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    Comparator<Product> highprice = new Comparator<Product>() {
                        @Override
                        public int compare(Product product, Product t1) {
                            return (t1.getPrice() - product.getPrice()) ;
                        }
                    } ;
                    Collections.sort(search_productList, highprice);
                    search_productListAdapter.notifyDataSetChanged();
                    break;
            }

            switch (setting_stat) {
                case 1:
                    productListView.setAdapter(search_productListAdapter);
                    break;
                case 2:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("새상품")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
                case 3:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("거의 새것")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
                case 4:
                    while(i < search_productListAdapter.getCount()) {
                        if(search_productList.get(i).getStat().equals("중고")) {
                            price_search_productList.add(search_productList.get(i));
                        }
                        i++;
                    }
                    price_search_productListAdapter = new ProductListAdapter(getApplicationContext(), price_search_productList);
                    productListView.setAdapter(price_search_productListAdapter);
                    break;
            }
        }
    }
}
