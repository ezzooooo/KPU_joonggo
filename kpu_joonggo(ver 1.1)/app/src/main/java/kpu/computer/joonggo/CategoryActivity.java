package kpu.computer.joonggo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

private String loginID;
private String userEmail;
private String userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        loginID = intent.getStringExtra("loginID");
        userEmail = intent.getStringExtra("userEmail");
        userNickname = intent.getStringExtra("userNickname");

        Button textbookbutton = (Button) findViewById(R.id.TextBookButton);
        Button generalbookbutton = (Button)findViewById(R.id.GeneralBookButton);
        Button electronicsbutton = (Button)findViewById(R.id.ElectronicsButton);
        Button clothesbutton = (Button)findViewById(R.id.ClothesButton);
        Button rentedroombutton =(Button)findViewById(R.id.RentedRoomButton);
        Button othersbutton = (Button)findViewById(R.id.OthersButton);

        textbookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 1);
                startActivity(intent);
                finish();
            }
        });

        generalbookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 2);
                startActivity(intent);
                finish();
            }
        });

        electronicsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 3);
                startActivity(intent);
                finish();
            }
        });

        clothesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 4);
                startActivity(intent);
                finish();
            }
        });

        rentedroombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 5);
                startActivity(intent);
                finish();
            }
        });

        othersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, InformationActivity.class);
                intent.putExtra("loginID", loginID);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userNickname", userNickname);
                intent.putExtra("select_category", 6);
                startActivity(intent);
                finish();
            }
        });

    }
}
