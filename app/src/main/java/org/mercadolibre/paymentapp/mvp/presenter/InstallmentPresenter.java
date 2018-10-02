package org.mercadolibre.paymentapp.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericPresenter;
import org.mercadolibre.paymentapp.mvp.common.global.Constants;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener;
import org.mercadolibre.paymentapp.mvp.model.GlobalModel;
import org.mercadolibre.paymentapp.mvp.model.data.Data;
import org.mercadolibre.paymentapp.mvp.model.data.ModelCardIssuer;
import org.mercadolibre.paymentapp.mvp.model.data.ModelInstallment;
import org.mercadolibre.paymentapp.mvp.model.netwotk.Client;
import org.mercadolibre.paymentapp.mvp.view.activities.InstallmentActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Enny Querales
 */
public class InstallmentPresenter
        extends GenericPresenter<MVP.RequiredPresenterMethods, MVP.ProvidedModelMethods, GlobalModel>
        implements MVP.ProvidedPresenterMethodsActivity, MVP.RequiredPresenterMethods {

    /**
     * Attributes
     */
    private WeakReference<MVP.RequiredActivityMethods> view;
    private Context context;
    private Listener.OnNetworkResponseListener listener = null;

    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(MVP.RequiredActivityMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);

        context = this.view.get().getActivityContext();
    }

    public void getInstallments(String amount, String paymentID, String issuerID) {
        Call<ResponseBody> call = Client.getRestAPIService().getInstallments(Constants.API_KEY, amount, paymentID, issuerID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (listener != null)
                        try {
                            listener.processResponse(Client.getAPIService(response.body().string(), ModelInstallment[].class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                } else {
                    listener.processFailure("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                try {
                    listener.processFailure("Something went wrong! \n" + throwable.getCause().toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public MVP.ProvidedModelMethods getModel() {
        return new GlobalModel(context);
    }

    @Override
    public <T> void handleClick(int viewId, Listener.OnNetworkResponseListener listener, T clazz) {
        this.listener = listener;
        Data data = (Data) clazz;
        getInstallments(data.getAmount(), data.getPaymentMethod(), data.getCardIssuer());
    }
}
