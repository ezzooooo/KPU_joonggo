package kpu.computer.joonggo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class InformationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText search_product;
    private Button product_search_button;
    private TextView nav_id;
    private TextView nav_email;
    private ImageView nav_image;
    private String loginID;
    private String userEmail;
    private String userNickname;
    private Integer select_category;

    private ListView productListView;
    private ProductListAdapter productListAdapter;
    private List<Product> productList;
    private AlertDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();

        loginID = intent.getStringExtra("loginID");
        userEmail = intent.getStringExtra("userEmail");
        userNickname = intent.getStringExtra("userNickname");
        select_category = intent.getIntExtra("select_category", 0);

        search_product = (EditText) findViewById(R.id.search_product);
        product_search_button = (Button) findViewById(R.id.product_search_button);
        productListView = (ListView) findViewById(R.id.productListView);
        productList = new ArrayList<Product>();
        productListAdapter = new ProductListAdapter(getApplicationContext(), productList);
        productListView.setAdapter(productListAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, MessageActivity.class);
                intent.putExtra("loginID", loginID);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View mHeaderView = navigationView.getHeaderView(0);

        nav_id = (TextView) mHeaderView.findViewById(R.id.nav_id);
        nav_email = (TextView) mHeaderView.findViewById(R.id.nav_email);
        nav_image = (ImageView) mHeaderView.findViewById(R.id.nav_imageView);

        nav_id.setText(userNickname);
        nav_email.setText(userEmail);
        nav_image.setImageResource(R.drawable.app_logo2);

        product_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(search_product.getText().toString().equals("")||search_product.getText().toString().matches(" ")){
                    AlertDialog.Builder buil = new AlertDialog.Builder(InformationActivity.this);
                    dialog = buil.setMessage("검색어를 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(InformationActivity.this, SearchProductActivity.class);
                    intent.putExtra("searchName", search_product.getText().toString());
                    intent.putExtra("loginID", loginID);
                    startActivity(intent);
                }
            }
        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(InformationActivity.this, PickProductActivity.class);
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

        new ProductTask().execute();
    }


    class ProductTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            productList.clear();
            target = "http://114.204.73.214/ProductSend.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("product");
                int count = 0;
                Integer productNumber, productPrice;
                String productSeller, productExp, productCategory, productName, productStat, imagePath1, imagePath2, imagePath3;

                while(count < jsonArray.length()) {
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
                    switch (select_category) {
                        case 1:
                            if(productCategory.equals("교재"))
                                productList.add(product);
                            break;
                        case 2:
                            if(productCategory.equals("일반도서"))
                                productList.add(product);
                            break;
                        case 3:
                            if(productCategory.equals("전자제품"))
                                productList.add(product);
                            break;
                        case 4:
                            if(productCategory.equals("의류"))
                                productList.add(product);
                            break;
                        case 5:
                            if(productCategory.equals("자취방"))
                                productList.add(product);
                            break;
                        case 6:
                            if(productCategory.equals("기타"))
                                productList.add(product);
                            break;
                    }
                    productListAdapter.notifyDataSetChanged();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.information, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InformationActivity.this, CategoryActivity.class);
        intent.putExtra("loginID", loginID)
                .putExtra("userEmail", userEmail)
                .putExtra("userNickname", userNickname);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_myproduct) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("loginID", loginID);
            intent.putExtra("product_kind", 0);
            startActivity(intent);
        }
        else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("loginID", loginID);
            intent.putExtra("product_kind", 1);
            startActivity(intent);

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(this, UserinfoActivity.class);
            intent.putExtra("loginID", loginID);
            startActivity(intent);

        } else if (id == R.id.free_item){
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("loginID",loginID);
            intent.putExtra("product_kind", 2);
            startActivity(intent);
        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(this, NoticeActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_product) {
            Intent intent = new Intent(this,ProductaddActivity.class);
            intent.putExtra("loginID",loginID);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("loginID", loginID);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("is_logout", true);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_questions) {
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
