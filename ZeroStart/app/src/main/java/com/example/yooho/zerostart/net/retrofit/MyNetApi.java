package com.example.yooho.zerostart.net.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface MyNetApi {

    @GET("{operation}")
    Call<ResponseBody> getMethod0(@Path("operation") String operation);

    // FormUrlEncoded 和 Field是对应的
    @POST("getUserInfoByUserNo.php")
    @FormUrlEncoded
    Call<ResponseBody> getMethod1(@Field("userNo") String userNo, @Field("cookie") String cookie, @Field("udid") String udid, @Field("searchUserNo") String searchUserNo);

    @POST("getUserInfoByUserNo.php")
    @FormUrlEncoded
    Call<ResponseBody> getMethod2(@FieldMap Map<String, String> map);


    @POST("getUserInfoByUserNo.php")
    @Multipart
    Call<ResponseBody> getMethod3(@Part("userNo") RequestBody userNo, @Part("cookie") RequestBody cookie,
                                  @Part("udid") RequestBody udid, @Part("searchUserNo") RequestBody searchUserNo,
                                  @Part MultipartBody.Part file);

    @POST("getUserInfoByUserNo.php")
    @Multipart
    Call<ResponseBody> getMethod4(@PartMap Map<String, RequestBody> map);
}
