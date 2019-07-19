package kpu.computer.joonggo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PickProductActivity extends AppCompatActivity {

    private boolean Image = true;
    ProductImageAdapter adapter;
    ViewPager viewPager;
    private Integer productNumber;
    private String imagePath1, imagePath2, imagePath3;
    private TextView kinds_TV;
    private TextView price_TV;
    private TextView stat_TV;
    private TextView info_TV;
    private TextView seller_TV;
    private String loginID, productSeller;
    private Button message_Button;
    private Button edit_Button, finish_button;
    private String productName;
    private Integer productPrice;
    private String productStat;
    private String productExp;
    private String productCategory;
    private ImageButton fav_btn;
    private boolean fav_tf = true;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_product);

        message_Button = (Button) findViewById(R.id.message_button);
        edit_Button = (Button) findViewById(R.id.product_edit_button);
        finish_button = (Button) findViewById(R.id.product_finish_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        kinds_TV = (TextView) findViewById(R.id.kinds_goods);
        price_TV = (TextView) findViewById(R.id.price_goods);
        stat_TV = (TextView) findViewById(R.id.state_goods);
        info_TV = (TextView) findViewById(R.id.info_goods);
        seller_TV = (TextView) findViewById(R.id.uniq_goods);

        final Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");
        productNumber = intent.getIntExtra("productNumber", -1);
        productPrice = intent.getIntExtra("productPrice", -1);

        setTitle(productName = intent.getStringExtra("productName"));
        kinds_TV.setText(productCategory = intent.getStringExtra("productCategory"));
        price_TV.setText("" + intent.getIntExtra("productPrice", -1));
        stat_TV.setText(productStat = intent.getStringExtra("productStat"));
        info_TV.setText(productExp = intent.getStringExtra("productExp"));
        seller_TV.setText(productSeller = intent.getStringExtra("productSeller"));
        imagePath1 = intent.getStringExtra("productImage1");
        imagePath2 = intent.getStringExtra("productImage2");
        imagePath3 = intent.getStringExtra("productImage3");

        adapter = new ProductImageAdapter(this, imagePath1, imagePath2, imagePath3);
        viewPager.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        fav_btn.setImageResource(R.drawable.ic_favorite_black);
                        fav_tf = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CheckLikeProductRequest checkLikeProductRequest = new CheckLikeProductRequest(loginID, productNumber, listener);
        RequestQueue queue = Volley.newRequestQueue(PickProductActivity.this);
        queue.add(checkLikeProductRequest);

        fav_btn = (ImageButton) findViewById(R.id.love_button);
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav_tf) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    fav_btn.setImageResource(R.drawable.ic_favorite_black);
                                    fav_tf = false;
                                    Toast.makeText(getApplicationContext(), "이 상품을 찜꽁하였습니다.>_<", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LikeProductRequest likeProductRequest = new LikeProductRequest(loginID, productNumber, listener);
                    RequestQueue queue = Volley.newRequestQueue(PickProductActivity.this);
                    queue.add(likeProductRequest);

                } else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    fav_btn.setImageResource(R.drawable.ic_favorite_border_black);
                                    fav_tf = true;
                                    Toast.makeText(getApplicationContext(), "이 상품을 찜해제 하였습니다.ㅠ_ㅠ", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    UnLikeProductRequest unLikeProductRequest = new UnLikeProductRequest(loginID, productNumber, listener);
                    RequestQueue queue = Volley.newRequestQueue(PickProductActivity.this);
                    queue.add(unLikeProductRequest);
                }

            }
        });

        message_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PickProductActivity.this, MessageSendActivity.class);
                intent1.putExtra("sender", loginID);
                intent1.putExtra("receiver", productSeller);
                startActivity(intent1);
            }
        });

        if (loginID.equals(productSeller)) {
            edit_Button.setVisibility(View.VISIBLE);
            finish_button.setVisibility(View.VISIBLE);
            message_Button.setVisibility(View.GONE);
        } else {
            edit_Button.setVisibility(View.GONE);
            finish_button.setVisibility(View.GONE);
            message_Button.setVisibility(View.VISIBLE);
        }

        edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PickProductActivity.this, ProductaddActivity.class);
                intent1.putExtra("productNumber", productNumber);
                intent1.putExtra("productName", productName);
                intent1.putExtra("productExp", productExp);
                intent1.putExtra("productCategory", productCategory);
                intent1.putExtra("productPrice", productPrice);
                intent1.putExtra("productStat", productStat);
                intent1.putExtra("is_edit", true);
                startActivity(intent1);
            }
        });

        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PickProductActivity.this);
                builder.setTitle("판매완료")
                        .setMessage("판매완료 시 상품이 삭제됩니다. 완료 하시겠습니까?")
                        .setCancelable(true)
                        .setPositiveButton("판매완료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            Boolean success = jsonObject.getBoolean("success");
                                            if (success) {
                                                Toast.makeText(PickProductActivity.this, "상품판매 완료!", Toast.LENGTH_SHORT);
                                                setResult(1);
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                ProductSaleFinishRequest productSaleFinishRequest = new ProductSaleFinishRequest(productNumber, listener);
                                RequestQueue queue = Volley.newRequestQueue(PickProductActivity.this);
                                queue.add(productSaleFinishRequest);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
}