package org.mercadolibre.paymentapp.mvp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Enny Querales.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    private static App mInstance;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
    }
}
