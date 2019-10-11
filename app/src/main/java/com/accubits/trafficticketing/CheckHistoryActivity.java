package com.accubits.trafficticketing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.accubits.trafficticketing.Model.History;
import com.accubits.trafficticketing.Model.LicensePlateDetectionResponse;
import com.accubits.trafficticketing.Rest.ApiClient;
import com.accubits.trafficticketing.Rest.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckHistoryActivity extends AppCompatActivity {
    RelativeLayout btnHistoryList;
    Button btnTakePhoto;
    EditText edtTxtLpNum;
    private Uri mCropImageUri,croppedImageUri;

    String lp_num;
    ArrayList<History> historyList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);

        btnHistoryList = findViewById(R.id.l_nav_to_history);
        btnTakePhoto   = findViewById(R.id.button_take_photo);
        edtTxtLpNum    = findViewById(R.id.editText_lp_num);

        edtTxtLpNum.setText("");
        hideStatusBar();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });

        btnHistoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp_num = edtTxtLpNum.getText().toString();
                if(lp_num.isEmpty()){
                    Toast.makeText(CheckHistoryActivity.this, "Please enter License Plate Number", Toast.LENGTH_SHORT).show();
                }else {
                    ShowViolationHistory(lp_num);
                }

            }
        });

    }

    private void hideStatusBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public void onSelectImageClick(View view) {

        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                croppedImageUri = result.getUri();
                Intent iConfirmation = new Intent(CheckHistoryActivity.this,ConfirmationActivity.class);
                iConfirmation.putExtra("cropped_image",croppedImageUri.toString());
                startActivity(iConfirmation);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ShowViolationHistory(String lp_num){
        ApiInterface getResponse = ApiClient.getClient().create(ApiInterface.class);
        Call<LicensePlateDetectionResponse> call = getResponse.getNoPlateDetectionReportAsText(lp_num,"string");
        call.enqueue(new Callback<LicensePlateDetectionResponse>() {
            @Override
            public void onResponse(Call<LicensePlateDetectionResponse> call, Response<LicensePlateDetectionResponse> response) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                LicensePlateDetectionResponse serverResponse = response.body();
                if (serverResponse != null) {
                    String status = serverResponse.getStatus();
                    historyList = serverResponse.getHistory();
                    // Toast.makeText(ConfirmationActivity.this, ""+historyList.toString(), Toast.LENGTH_SHORT).show();
                    Intent iHistoryList = new Intent(CheckHistoryActivity.this, NewViolationAndHistoryActivity.class);
                    iHistoryList.putExtra("History_list", historyList);
                    startActivity(iHistoryList);
                } else {
                    Toast.makeText(CheckHistoryActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LicensePlateDetectionResponse> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(CheckHistoryActivity.this, "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
