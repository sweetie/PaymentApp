package org.mercadolibre.paymentapp.mvp.common.generic;

import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.interfaces.ModelMethods;
import org.mercadolibre.paymentapp.mvp.common.utilities.Logger;

/**
 * This class provides a framework that enables a Presenter to access an object residing in the
 * Model layer in the Model-View-Presenter (MVP) pattern.
 *
 * The three types used by a GenericActivity are the following:
 *
 * RequiredPresenterMethods (RPM): the class or interface that defines the methods available to the
 * Model object from the Presenter layer.
 *
 * ProvidedModelMethods (PMM: the class or interface that defines the methods available to the
 * Presenter layer from the Model object.
 *
 * Model (M): the class used by the GenericPresenter framework to instantiate a Model object.
 *
 * Modified by
 * @author Enny Querales
 */
public abstract class GenericPresenter<RPM, PMM, M extends ModelMethods<RPM>> {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private M model;


    /**
     * Lifecycle hook method that's called when the GenericPresenter is created.
     *
     * @param model Class object that's used to create an model object.
     * @param presenter Reference to the RequiredPresenterMethods object.
     */
    public void onCreate(Class<M> model, RPM presenter) {
        //noinspection TryWithIdenticalCatches
        try {
            initialize(model, presenter);
        } catch (InstantiationException e) {
            logger.log("handleConfiguration - " + e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.log("handleConfiguration - " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the GenericPresenter fields.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initialize(Class<M> model, RPM presenter)
        throws InstantiationException, IllegalAccessException {

        // Create the Model object.
        this.model = model.newInstance();

        // Perform the first initialization.
        this.model.onCreate(presenter);
    }

    /***
     * Return the initialized ProvidedMethods instance for use by the Presenter layer.
     */
    @SuppressWarnings("unchecked")
    public PMM getModel() {
        return (PMM) this.model;
    }

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view active RequiredFragmentMethods
     */
    public void onConfigurationChange(MVP.RequiredFragmentMethods view) {
        // Implemented on concrete presenter if needed
    }

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view active RequiredActivityMethods
     */
    public void onConfigurationChange(MVP.RequiredActivityMethods view) {
        // no-op
    }

    /**
     * Hook method called when the Presenter object is destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy call.
     */
    public void onDestroy(boolean isChangingConfigurations) {
        // Implemented on concrete presenter if needed
    }

    /**
     * Hook method dispatched by an activity or fragment indicating the id of the view element that
     * was pressed. The presenter will perform the appropriate action.
     *
     * @param viewId view Id
     */
    public void handleClick(int viewId) {
        // Implemented on concrete presenter if needed
    }

    /**
     * Hook method dispatched by an activity or fragment.
     * The presenter will perform the appropriate network call.
     *
     */
    public <T> void executeNetworkRequest() {
        // Implemented on concrete presenter if needed
    }

    /**
     * Hook method dispatched by an activity or fragment passing in the data model associated.
     * The presenter will perform the appropriate network call.
     *
     * @param model data model (POJO class)
     */
    public <T> void executeNetworkRequest(T model) {
        // Implemented on concrete presenter if needed
    }
}

