package com.accubits.trafficticketing.Rest;

import com.accubits.trafficticketing.Model.LicensePlateDetectionResponse;
import com.accubits.trafficticketing.Model.NewViolationResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("license_plate_detection_plates")
    Call<LicensePlateDetectionResponse> getNoPlateDetectionReportAsImage(@Part MultipartBody.Part filePart,@Part("type") String type);

    @Multipart
    @POST("license_plate_detection_plates")
    Call<LicensePlateDetectionResponse> getNoPlateDetectionReportAsText(@Part("data") String data,@Part("type") String type);

    @POST("new_violation")
    @FormUrlEncoded
    Call<NewViolationResponse> getSendMailResponse(@Field("name") String name,@Field("lp_num") String lpNum,@Field("violation")
            String violation,@Field("amount") String amount,@Field("paid") String paid);

}
