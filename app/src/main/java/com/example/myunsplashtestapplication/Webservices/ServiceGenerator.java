package com.example.myunsplashtestapplication.Webservices;

import com.example.myunsplashtestapplication.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myunsplashtestapplication.Utils.Constants.BASE_API_URL;

public class ServiceGenerator {
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Client-ID " + Constants.YOUR_ACCESS_KEY)
                            .build();
                    return chain.proceed(request);
                }
            });

    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

//    public static <T> T createService(Class<T> serviceClass) {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl(Constants.BASE_API_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit.create(serviceClass);
//    }

    public static Retrofit getApiClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}