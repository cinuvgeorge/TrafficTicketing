package com.accubits.trafficticketing;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.accubits.trafficticketing.Model.History;
import com.accubits.trafficticketing.Model.LicensePlateDetectionResponse;
import com.accubits.trafficticketing.Rest.ApiClient;
import com.accubits.trafficticketing.Rest.ApiInterface;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends AppCompatActivity {
    Button btnRetake,btnProceed;
    ImageView imgCroppedView;
    String croppedImg;
    Uri uriCroppedImg;
    ArrayList<History> historyList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        btnRetake      = findViewById(R.id.button_retake);
        btnProceed     = findViewById(R.id.button_proceed);
        imgCroppedView = findViewById(R.id.imageView_cropped_img);

        hideStatusBar();
        showCroppedImageView();

        btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uriCroppedImg != null && !uriCroppedImg.equals(Uri.EMPTY)) {
                    SetLicensePlateDetectionUsingImage(uriCroppedImg);
                }
            }
        });

    }

    private void showCroppedImageView(){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                croppedImg= null;
            } else {
                croppedImg= extras.getString("cropped_image");
            }
        uriCroppedImg = Uri.parse(croppedImg);
        imgCroppedView.setImageURI(uriCroppedImg);
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

    public void SetLicensePlateDetectionUsingImage(Uri uri){
            File file = new File(uri.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

            ApiInterface getResponse = ApiClient.getClient().create(ApiInterface.class);
            Call<LicensePlateDetectionResponse> call = getResponse.getNoPlateDetectionReportAsImage(fileToUpload,"image");
            call.enqueue(new Callback<LicensePlateDetectionResponse>() {
                @Override
                public void onResponse(Call<LicensePlateDetectionResponse> call, Response<LicensePlateDetectionResponse> response) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    LicensePlateDetectionResponse serverResponse = response.body();
                    if (serverResponse != null) {
                        String status = serverResponse.getStatus();
                        historyList = serverResponse.getHistory();
                        // Toast.makeText(ConfirmationActivity.this, ""+historyList.toString(), Toast.LENGTH_SHORT).show();
                        Intent iHistoryList = new Intent(ConfirmationActivity.this, NewViolationAndHistoryActivity.class);
                        iHistoryList.putExtra("History_list", historyList);
                        startActivity(iHistoryList);
                    } else {
                        Toast.makeText(ConfirmationActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LicensePlateDetectionResponse> call, Throwable t) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(ConfirmationActivity.this, "failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });


    }
}
