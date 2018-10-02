package org.mercadolibre.paymentapp.mvp.model.netwotk;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Enny Querales
 */
public class Client {

    /**
     * Attributes
     */
    private static final String host = "https://api.mercadopago.com/v1/";
    private static RestAPIService restAPIService = null;
    private static Retrofit retrofit;
    private static boolean hasHostChanged = false;


    private Client(Retrofit retrofit) {
        restAPIService = retrofit.create(RestAPIService.class);
    }

    public static void changeBaseHost(String newHost) {
        hasHostChanged = true;
        retrofit = new Retrofit.Builder()
            .baseUrl(newHost)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static void returnToBaseHost() {
        hasHostChanged = false;
    }

    private static OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getInterceptor());
        return builder;
    }

    private static HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private static Retrofit getDefaultRetrofitBuilder () {
        return new Retrofit.Builder()
            .baseUrl(host)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static RestAPIService getRestAPIService() {
        if (restAPIService == null || !hasHostChanged)
            new Client(getDefaultRetrofitBuilder());
        return restAPIService;
    }

    public static RestAPIService getTestAPIService() {
        if (restAPIService == null || hasHostChanged)
            new Client(retrofit);
        return restAPIService;
    }

    public static <T> List<T> getAPIService(String s, Class<T[]> clazz) {
        List<T> list =null;
        try {
            list = Arrays.asList(new Gson().fromJson(s, clazz));
        } catch (JsonSyntaxException ex) {
            System.out.println(ex);
        }
        return list;
    }


}
