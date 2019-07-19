package kpu.computer.joonggo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductaddActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private String productName;
    private String productExp;
    private String productCategory;
    private String productPrice;
    private String productStat;
    private String productImage1 = "", productImage2 = "", productImage3 = "";
    private ImageButton imageButton1, imageButton2, imageButton3;
    private Spinner spinner;
    private AlertDialog dialog;
    private Button newButton, almostnewButton, usedButton, badButton;
    private Uri photoURI, albumURI;
    private String mCurrentPhotoPath1, mCurrentPhotoPath2, mCurrentPhotoPath3;
    private String productSeller;
    private Integer productNumber;
    private Boolean is_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productadd);

        final EditText productNameText = (EditText) findViewById(R.id.productNameText);
        final EditText productExpText = (EditText) findViewById(R.id.productexpText);
        final EditText productPriceText = (EditText) findViewById(R.id.productPriceText);
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        newButton = (Button) findViewById(R.id.productstatnewButton);
        almostnewButton = (Button) findViewById(R.id.productstatalmostnewButton);
        usedButton = (Button) findViewById(R.id.productstatusedButton);
        badButton = (Button) findViewById(R.id.productstatbadButton);

        imageButton1 = (ImageButton) findViewById(R.id.productimageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.productimageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.productimageButton3);
        Button addFinishButton = (Button) findViewById(R.id.productaddFinishButton);

        Intent intent = getIntent();

        is_edit = intent.getBooleanExtra("is_edit", false);

        if(is_edit) {
            productNumber = intent.getIntExtra("productNumber", 0);
            productNameText.setText(intent.getStringExtra("productName"));
            productExpText.setText(intent.getStringExtra("productExp"));
            productPriceText.setText("" + intent.getIntExtra("productPrice", 0));
            productCategory = intent.getStringExtra("productCategory");
            if(productCategory.equals("교재"))
                spinner.setSelection(0);
            else if(productCategory.equals("일반도서"))
                spinner.setSelection(1);
            else if(productCategory.equals("전자제품"))
                spinner.setSelection(2);
            else if(productCategory.equals("의류"))
                spinner.setSelection(3);
            else if(productCategory.equals("자취방"))
                spinner.setSelection(4);
            else
                spinner.setSelection(5);

            productStat = intent.getStringExtra("productStat");

            if(productStat.equals("새상품")) {
                newButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = newButton.getText().toString();
            }
            else if(productStat.equals("거의 새것")) {
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = almostnewButton.getText().toString();
            }
            else if(productStat.equals("중고")) {
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = usedButton.getText().toString();
            }
            else {
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                productStat = badButton.getText().toString();
            }

            addFinishButton.setText("수정");

            imageButton1.setVisibility(View.GONE);
            imageButton2.setVisibility(View.GONE);
            imageButton3.setVisibility(View.GONE);
        }

        productSeller = intent.getStringExtra("loginID");

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck1 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int permissionCheck2 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.CAMERA);

                if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductaddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                } else {
                    getAlbum(1);
                }
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck1 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int permissionCheck2 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.CAMERA);

                if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductaddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2);
                } else {
                    getAlbum(2);
                }
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck1 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int permissionCheck2 = ContextCompat.checkSelfPermission(ProductaddActivity.this, Manifest.permission.CAMERA);

                if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductaddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 3);
                } else {
                    getAlbum(3);
                }
            }
        });

        productStat = newButton.getText().toString();

        addFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productName = productNameText.getText().toString();
                productExp = productExpText.getText().toString();
                productCategory = spinner.getSelectedItem().toString();
                productPrice = productPriceText.getText().toString();

                if(productName.equals("") || productExp.equals("") || productCategory.equals("") || productPrice.equals("") || productStat.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductaddActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(is_edit) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductaddActivity.this);
                                    dialog = builder.setMessage("상품 수정에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductaddActivity.this);
                                    dialog = builder.setMessage("상품 수정에 실패했습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ProductEditRequest productEditRequest = new ProductEditRequest(productNumber, productName, productExp, productCategory, productPrice, productStat, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProductaddActivity.this);
                    queue.add(productEditRequest);
                }

                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductaddActivity.this);
                                    dialog = builder.setMessage("상품 등록에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductaddActivity.this);
                                    dialog = builder.setMessage("상품 등록에 실패했습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ProductaddRequest productaddRequest = new ProductaddRequest(productSeller, productName, productExp, productCategory, productPrice, productStat, productImage1, productImage2, productImage3, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProductaddActivity.this);
                    queue.add(productaddRequest);
                    uploadFile(mCurrentPhotoPath1);
                    uploadFile(mCurrentPhotoPath2);
                    uploadFile(mCurrentPhotoPath3);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getAlbum(1);
                } else {
                    return;
                }
                return ;
            }
            case 2: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getAlbum(2);
                } else {
                    return;
                }
                return ;
            }
            case 3: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getAlbum(3);
                } else {
                    return;
                }
                return ;
            }

        }
    }

    private void getAlbum(Integer num) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, num);
    }

    public File createImageFile(Integer num) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = productSeller + "_" + timeStamp + ".jpg";
        switch (num) {
            case 1: {
                productImage1 = imageFileName;
                break;
            }
            case 2: {
                productImage2 = imageFileName;
                break;
            }
            case 3: {
                productImage3 = imageFileName;
                break;
            }
        }

        File imageFile = null;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Pictures", "KPUjoonggo");

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        switch (num) {
            case 1: {
                mCurrentPhotoPath1 = imageFile.getAbsolutePath();
                break;
            }
            case 2: {
                mCurrentPhotoPath2 = imageFile.getAbsolutePath();
                break;
            }
            case 3: {
                mCurrentPhotoPath3 = imageFile.getAbsolutePath();
                break;
            }
        }

        return imageFile;
    }

    public void cropImage(Integer num) {

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("outputX", 500);
        cropIntent.putExtra("outputY", 500);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);
        startActivityForResult(cropIntent, num);
    }

    public void uploadFile(String filePath) {
        String url = "http://114.204.73.214/ImageUpload.php";
        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(ProductaddActivity.this);
            uploadFileRequest.setPath(filePath);
            uploadFileRequest.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 || requestCode == 2 || requestCode == 3) {
            if(resultCode == RESULT_OK) {
                if(data.getData() != null) {
                    try {
                        File albumFile = null;
                        albumFile = createImageFile(requestCode);
                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);
                        switch (requestCode) {
                            case 1: {
                                cropImage(4);
                                break;
                            }
                            case 2: {
                                cropImage(5);
                                break;
                            }
                            case 3: {
                                cropImage(6);
                                break;
                            }
                        }

                    } catch (Exception e) {
                    }
                }
            }
        }
        else if(requestCode == 4 || requestCode ==5 || requestCode == 6) {
            if(resultCode == RESULT_OK) {
                galleryAddPic(requestCode);
                switch (requestCode) {
                    case 4: {
                        imageButton1.setImageURI(albumURI);
                        imageButton1.setScaleType(ImageView.ScaleType.FIT_XY);
                        break;
                    }
                    case 5: {
                        imageButton2.setImageURI(albumURI);
                        imageButton2.setScaleType(ImageView.ScaleType.FIT_XY);
                        break;
                    }
                    case 6: {
                        imageButton3.setImageURI(albumURI);
                        imageButton3.setScaleType(ImageView.ScaleType.FIT_XY);
                        break;
                    }
                }
            }
        }
    }

    private void galleryAddPic(Integer num) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = null;
        switch (num) {
            case 4: {
               f = new File(mCurrentPhotoPath1);
               break;
            }
            case 5: {
               f = new File(mCurrentPhotoPath2);
                break;
            }
            case 6: {
               f = new File(mCurrentPhotoPath3);
                break;
            }
        }
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void StatClick(View v) {

        switch (v.getId()) {
            case R.id.productstatnewButton:
                newButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = newButton.getText().toString();
                break;
            case R.id.productstatalmostnewButton:
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = almostnewButton.getText().toString();
                break;
            case R.id.productstatusedButton:
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                productStat = usedButton.getText().toString();
                break;
            case R.id.productstatbadButton:
                newButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                almostnewButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                usedButton.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                badButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                productStat = badButton.getText().toString();
                break;
        }
    }
}
