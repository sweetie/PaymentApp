package org.mercadolibre.paymentapp.mvp;

import android.content.Context;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.mercadolibre.paymentapp.mvp.common.generic.GenericActivity;
import org.mercadolibre.paymentapp.mvp.common.global.Enums.DialogType;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener;
import org.mercadolibre.paymentapp.mvp.common.interfaces.ModelMethods;
import org.mercadolibre.paymentapp.mvp.common.interfaces.PresenterMethods;
import org.mercadolibre.paymentapp.mvp.common.managers.RetainedFragmentManager;

/**
 * Defines the interfaces for this application that are required and provided by the layers
 * in the MVP (Model-View-Presenter) pattern. This design ensures loose coupling between the
 * layers in the app's MVP-based architecture.
 */
public interface MVP {

    /**
     * This interface allows the View layer to display Dialogs and Toast Messages on activities or
     * fragments without knowing any implementation details.
     */
    interface RequiredActivityMethods {

        /**
         * Return the RetainedFragmentManager.
         */
        RetainedFragmentManager getRetainedFragmentManager();

        /**
         * Toolbar setter
         *
         * @param toolbar Object
         * @param isHomeEnable true if it should return to previous activity
         */
        void setToolbar(Toolbar toolbar, boolean isHomeEnable);

        /**
         * Toolbar getter
         *
         * @return Toolbar
         */
        Toolbar getToolbar();

        /**
         * Get the Activity Context.
         */
        Context getActivityContext();

        /**
         * Get the Application Context.
         */
        Context getApplicationContext();

        /**
         * Starts another activity
         *
         * @param activityClass class parameter
         */
        void startActivity(Class<?> activityClass);

        /**
         * Allows fragment placement in the activity
         *
         * @param containerViewId container layout id
         * @param fragment fragment instance
         */
        <T extends Fragment> void placeFragment(int containerViewId, T fragment);

        /**
         * Allows fragment replacement in the activity
         *
         * @param containerViewId container layout id
         * @param fragment fragment instance
         * @param isStacked indicates whether fragment should be pushed to the back stack
         */
        <T extends Fragment> void replaceFragment(int containerViewId, T fragment, boolean isStacked);

        /**
         * Replace a view with another
         *
         * @param id Id of the view that is going to be replaced
         * @param replacement Replacement view object
         */
        void replaceView(int id, View replacement);

        /**
         * This method is used to hide a keyboard after a user has finished typing the url.
         *
         * @param windowToken the token of the window that is making the request, returned by
         * View.getWindowToken()
         */
        void hideKeyboard(IBinder windowToken);

        /**
         * Display a toast for a default duration (short) on the screen
         *
         * @param message the message pass as resource of type string (example: R.string.message)
         * @param length the length of the toast (short, long, etc)
         */
        void displayToast(String message, int length);

        /**
         * Sets a custom dialog view to be displayed on request of the fragment
         */
        void setDialog(View view);

        /**
         * Displays dialog with type {@param type}
         *
         * @param type chosen type
         */
        void displayDialog(DialogType type);

        /**
         * Dismiss dialog with type {@param type}
         *
         * @param type chosen type
         */
        void dismissDialog(DialogType type);

        /**
         * Renders a snack bar at the bottom of the screen
         *
         * @param view parent view
         * @param message resource ID with desired message
         * @param close resource ID with desired action title
         */
        void displaySnackBar(View view, int message, int close, View.OnClickListener listener);

        void showTitleAndMessageDialog(Context context, String title, String message);

        /**
         * Update fragment view content with new {@param data}
         */
        <T> void updateView(T data);
    }

    interface RequiredFragmentMethods {

        /**
         * Return Activity's context
         */
        Context getActivityContext();

        /**
         * Return parent Activity
         */
        <T extends GenericActivity> T getParentActivity(Class<T> classType);

        /**
         * Update fragment view content with new {@param data}
         */
        <T> void updateView(T data);

        /**
         * Sets a custom dialog view to be displayed on request of the fragment
         */
        void setDialog(View view);

        /**
         * Displays dialog with type {@param type}
         *
         * @param type chosen type
         */
        void displayDialog(DialogType type);

        /**
         * Dismiss dialog with type {@param type}
         *
         * @param type chosen type
         */
        void dismissDialog(DialogType type);

    }


    /**
     * This interface allows the View layer to call methods in the Presenter layer without knowing
     * any implementation details.
     */
    interface ProvidedPresenterMethodsActivity
            extends PresenterMethods<RequiredActivityMethods> {

        //void handleClick(int viewId);

        <T> void handleClick(int viewId, Listener.OnNetworkResponseListener listener, T clazz);

        <T> void executeNetworkRequest(T model);

        <PMM> PMM getModel();
    }

    /**
     * This interface allows the View layer to call methods in the Presenter layer without knowing
     * any implementation details.
     */
    interface ProvidedPresenterMethodsFragment
            extends PresenterMethods<RequiredFragmentMethods> {

        //void handleClick(int viewId);

        <T> void handleClick(int viewId, Listener.OnNetworkResponseListener listener, T clazz);

        <T> void executeNetworkRequest();

        <PMM> PMM getModel();
    }


    /**
     * This interface is a no-op since the Model layer doesn't require any methods from the
     * Presenter layer.
     */
    interface RequiredPresenterMethods {
        // no-op
    }

    /**
     * This interface allows the Presenter layer to call methods in the Model layer without knowing
     * any implementation details.
     */
    interface ProvidedModelMethods
           extends ModelMethods<RequiredPresenterMethods> {

        /**
         * Saves any value on a general shared preference file
         *
         * @param key chosen key name
         * @param value chosen value type
         */
        <T> void saveOnSharedPreference(String key, T value);

        /**
         * Retrieves value from the general shared preference file
         *
         * @param key chosen key name
         * @param defaultValue expected value if no associated value exist
         */
        <T> Object getFromSharedPreference(Class<T> classType, String key, T defaultValue);
    }
}
