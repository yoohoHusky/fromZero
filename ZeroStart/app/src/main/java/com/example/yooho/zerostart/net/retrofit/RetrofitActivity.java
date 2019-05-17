package com.example.yooho.zerostart.net.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yooho.zerostart.R;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        initFindView();
    }

    private void initFindView() {
        MyListener listener = new MyListener();
        findViewById(R.id.retrofit_btn_0).setOnClickListener(listener);
        findViewById(R.id.retrofit_btn_1).setOnClickListener(listener);
        findViewById(R.id.retrofit_btn_2).setOnClickListener(listener);
        findViewById(R.id.retrofit_btn_3).setOnClickListener(listener);
        findViewById(R.id.retrofit_btn_4).setOnClickListener(listener);
    }

    /**
     *      * userNo
     *      * cookie
     *      * udid
     *      * searchUserNo
     */

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.retrofit_btn_0:
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.baidu.com/").build();
                    MyNetApi netApi = retrofit.create(MyNetApi.class);
                    Call<ResponseBody> call = netApi.getMethod0("method1");
                    printCall(call);

                    break;
                case R.id.retrofit_btn_1:
                    Retrofit retrofit2 = new Retrofit.Builder().baseUrl("http://developer.53site.com/AIIM/test/").build();
                    MyNetApi netApi2 = retrofit2.create(MyNetApi.class);
                    int a = 2;
                    switch (a) {
                        case 0:
                            Call<ResponseBody> call0 = netApi2.getMethod1("276007832287969281",
                                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJubyI6IjI3NjAwNzgzMjI4Nzk2OTI4MSIsImlkIjoiMmM3Zjc5ODctYWVmMy00NzY4LTkyZGQtZTcyMWFmODE5NDk1IiwidGltZSI6MTU1MDEzNjUyOH0.u2XolEFU-QNWnHt2lMw1T5Bdp2l25jHAOTmMx27rcCQ",
                                    "2c7f7987-aef3-4768-92dd-e721af819495",
                                    "276007832287969281");
                            printCall(call0);
                            break;
                        case 1:
                            HashMap<String, String> map = new HashMap<>();
                            map.put("userNo", "276007832287969281");
                            map.put("cookie", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJubyI6IjI3NjAwNzgzMjI4Nzk2OTI4MSIsImlkIjoiMmM3Zjc5ODctYWVmMy00NzY4LTkyZGQtZTcyMWFmODE5NDk1IiwidGltZSI6MTU1MDEzNjUyOH0.u2XolEFU-QNWnHt2lMw1T5Bdp2l25jHAOTmMx27rcCQ");
                            map.put("udid", "2c7f7987-aef3-4768-92dd-e721af819495");
                            map.put("searchUserNo", "276007832287969281");
                            Call<ResponseBody> call1 = netApi2.getMethod2(map);
                            printCall(call1);
                            break;
                        case 2:
                            MediaType textType = MediaType.parse("text/plain");
                            RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);

                            Call<ResponseBody> call2 = netApi2.getMethod3(
                                    RequestBody.create(textType, "276007832287969281"),
                                    RequestBody.create(textType, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJubyI6IjI3NjAwNzgzMjI4Nzk2OTI4MSIsImlkIjoiMmM3Zjc5ODctYWVmMy00NzY4LTkyZGQtZTcyMWFmODE5NDk1IiwidGltZSI6MTU1MDEzNjUyOH0.u2XolEFU-QNWnHt2lMw1T5Bdp2l25jHAOTmMx27rcCQ"),
                                    RequestBody.create(textType, "2c7f7987-aef3-4768-92dd-e721af819495"),
                                    RequestBody.create(textType, "276007832287969281"),
                                    filePart);
                            printCall(call2);
                            break;
                        case 3:
                            HashMap<String, RequestBody> map2 = new HashMap<>();
                            MediaType textType2 = MediaType.parse("text/plain");
                            RequestBody file2 = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

                            map2.put("userNo", RequestBody.create(textType2, "276007832287969281"));
                            map2.put("cookie", RequestBody.create(textType2, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJubyI6IjI3NjAwNzgzMjI4Nzk2OTI4MSIsImlkIjoiMmM3Zjc5ODctYWVmMy00NzY4LTkyZGQtZTcyMWFmODE5NDk1IiwidGltZSI6MTU1MDEzNjUyOH0.u2XolEFU-QNWnHt2lMw1T5Bdp2l25jHAOTmMx27rcCQ"));
                            map2.put("udid", RequestBody.create(textType2, "2c7f7987-aef3-4768-92dd-e721af819495"));
                            map2.put("searchUserNo", RequestBody.create(textType2, "276007832287969281"));
                            map2.put("file0\"; filename=\"test.txt", file2);
                            Call<ResponseBody> call3 = netApi2.getMethod4(map2);
                            printCall(call3);
                            break;
                    }
                    break;
                case R.id.retrofit_btn_2:
                    break;
                case R.id.retrofit_btn_3:
                    break;
                case R.id.retrofit_btn_4:
                    break;
            }
        }
    }

    private void printCall(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("SS", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("SS", "onFailure");
            }
        });
    }
}
