package org.mercadolibre.paymentapp.mvp.common.interfaces;

/**
 * The base interface that a Model class must implement.
 *
 * Modified by
 * @author Enny Querales
 */
public interface ModelMethods<RPM> {

    /**
     * Hook method dispatched by the GenericModel framework to initialize a Model object
     * after it's been instantiated.
     *
     * @param presenter The currently active RequiredModelMethods.
     */
    void onCreate(RPM presenter);

    /**
     * Hook method called when the Model object is destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy() call.
     */
    void onDestroy(boolean isChangingConfigurations);
}
