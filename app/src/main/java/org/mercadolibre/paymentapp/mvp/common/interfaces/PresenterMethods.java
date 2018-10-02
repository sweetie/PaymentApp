package org.mercadolibre.paymentapp.mvp.common.interfaces;

/**
 * The base interface that a Presenter class must implement.
 *
 * Modified by
 * @author Enny Querales
 */
public interface PresenterMethods<RVM> {

    /**
     * Hook method dispatched by the GenericActivity framework to initialize a Presenter object
     * after it's been instantiated.
     *
     * @param view The currently active RequiredViewMethods.
     */
    void onCreate(RVM view);

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view The currently active RequiredViewMethods.
     */
    void onConfigurationChange(RVM view);

    /**
     * Hook method called when the Presenter object is destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy call.
     */
    void onDestroy(boolean isChangingConfigurations);
}