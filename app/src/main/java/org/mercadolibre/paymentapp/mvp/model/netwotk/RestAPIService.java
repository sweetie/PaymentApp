package org.mercadolibre.paymentapp.mvp.model.netwotk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Enny Querales
 */
public interface RestAPIService {

    @GET("payment_methods/")
    Call<ResponseBody> getPaymentMethods(
            @Query("public_key") String key
    );

    @GET("payment_methods/card_issuers")
    Call<ResponseBody> getCardIssuers(
            @Query("public_key") String key,
            @Query("payment_method_id") String paymentID
    );

    @GET("payment_methods/installments")
    Call<ResponseBody> getInstallments(
            @Query("public_key") String key,
            @Query("amount") String amount,
            @Query("payment_method_id") String paymentID,
            @Query("issuer.id") String issuerID
    );
}
