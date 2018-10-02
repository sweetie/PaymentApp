package org.mercadolibre.paymentapp.mvp.common.generic;

import android.content.Context;

import java.lang.ref.WeakReference;

import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.managers.SharedPreferenceManager;

/**
 * Created by Enny Querales
 */
public abstract class GenericModel
        implements MVP.ProvidedModelMethods {

    /**
     * Attributes
     */
    protected Context context;
    protected WeakReference<MVP.RequiredPresenterMethods> presenter;


    /**
     * Default constructor required by framework
     */
    protected GenericModel() {
        // no-op
    }

    /**
     * Parameter constructor
     *
     * @param context application/activity context
     **/
    public GenericModel(Context context) {
        this.context = context;
    }

    /**
     * Hook method called when a new instance of a Model is created.
     *
     * @param presenter a reference to the Presenter layer
     */
    @Override
    public void onCreate(MVP.RequiredPresenterMethods presenter) {
        this.presenter = new WeakReference<>(presenter);
    }

    /**
     * Hook method called to shutdown the Model layer
     *
     * @param isChangingConfiguration True if a runtime configuration triggered the onDestroy call
     */
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        // no-op
    }

    /**
     * Saves any value on a general shared preference file
     *
     * @param key   chosen key name
     * @param value chosen value type
     */
    @Override
    public <T> void saveOnSharedPreference(String key, T value) {
        SharedPreferenceManager.putValue(context, key, value);
    }

    /**
     * Retrieves value from the general shared preference file
     *
     * @param key          chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    @Override
    public <T> T getFromSharedPreference(Class<T> classType, String key, T defaultValue) {
        return SharedPreferenceManager.getValue(context, classType, key, defaultValue);
    }
}
