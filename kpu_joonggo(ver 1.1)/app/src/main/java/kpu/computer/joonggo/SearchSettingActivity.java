package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchSettingActivity extends AppCompatActivity {

    Button setting_newest, setting_lowprice, setting_highprice;
    Button setting_wholeproduct, setting_newproduct, setting_almostnewproduct, setting_usedproduct;
    Button setting_clear, setting_commit;
    EditText setting_minprice, setting_maxprice;
    private int setting_sort=1;
    private int setting_stat=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_setting);

        setting_newest = (Button) findViewById(R.id.setting_newest);
        setting_lowprice = (Button) findViewById(R.id.setting_lowprice);
        setting_highprice = (Button) findViewById(R.id.setting_highprice);
        setting_wholeproduct = (Button) findViewById(R.id.setting_wholeproduct);
        setting_newproduct = (Button) findViewById(R.id.setting_newproduct);
        setting_almostnewproduct = (Button) findViewById(R.id.setting_almostnewproduct);
        setting_usedproduct = (Button) findViewById(R.id.setting_usedproduct);
        setting_clear = (Button) findViewById(R.id.setting_clear);
        setting_commit = (Button) findViewById(R.id.setting_commit);

        setting_minprice = (EditText) findViewById(R.id.setting_minprice);
        setting_maxprice = (EditText) findViewById(R.id.setting_maxprice);

        Intent get_intent = getIntent();
        setting_sort = get_intent.getIntExtra("setting_sort", 1);
        setting_stat = get_intent.getIntExtra("setting_stat", 1);

        switch (setting_sort) {
            case 1:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_sort = 1;
                break;
            case 2:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_sort = 2;
                break;
            case 3:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_sort = 3;
                break;
        }

        switch (setting_stat) {
            case 1 :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 1;
                break;
            case 2 :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 2;
                break;
            case 3 :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 3;
                break;
            case 4 :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_stat = 4;
                break;
        }

        setting_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_sort = 1;

                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 1;

                setting_minprice.setText("");
                setting_maxprice.setText("");
            }
        });

        setting_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (setting_minprice.getText().toString().equals("") && setting_maxprice.getText().toString().equals("")){
                    setResult(1, intent);
                } else if(setting_minprice.getText().toString().equals("") && !(setting_maxprice.getText().toString().equals("")) ){
                    setResult(2, intent);
                    intent.putExtra("setting_maxprice", setting_maxprice.getText().toString());
                } else if( !(setting_minprice.getText().toString().equals("")) && (setting_maxprice.getText().toString().equals("")) ) {
                    setResult(3, intent);
                    intent.putExtra("setting_minprice", setting_minprice.getText().toString());
                } else if(Integer.parseInt(setting_minprice.getText().toString()) > Integer.parseInt(setting_maxprice.getText().toString())) {
                    Toast.makeText(SearchSettingActivity.this, "최소가격이 최대가격보다 높습니다.", Toast.LENGTH_SHORT).show();
                }
                 else {
                    setResult(4, intent);
                    intent.putExtra("setting_minprice", setting_minprice.getText().toString());
                    intent.putExtra("setting_maxprice", setting_maxprice.getText().toString());
                }

                intent.putExtra("setting_sort", setting_sort);
                intent.putExtra("setting_stat", setting_stat);
                finish();
            }
        });
    }

    public void setting_sort (View view) {
        switch (view.getId()) {
            case R.id.setting_newest:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_sort = 1;
                break;
            case R.id.setting_lowprice:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_sort = 2;
                break;
            case R.id.setting_highprice:
                setting_newest.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_lowprice.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_highprice.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_sort = 3;
                break;
        }
    }

    public void setting_stat (View view) {
        switch (view.getId()) {
            case R.id.setting_wholeproduct :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 1;
                break;
            case R.id.setting_newproduct :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 2;
                break;
            case R.id.setting_almostnewproduct :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_stat = 3;
                break;
            case R.id.setting_usedproduct :
                setting_wholeproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_newproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_almostnewproduct.setBackground(getResources().getDrawable(R.drawable.rounded_serchsetting));
                setting_usedproduct.setBackground(getResources().getDrawable(R.drawable.rounded_primary));
                setting_stat = 4;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        finish();
    }
}
