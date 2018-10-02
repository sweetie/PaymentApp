package org.mercadolibre.paymentapp.mvp.common.generic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mercadolibre.paymentapp.mvp.common.utilities.Logger;

/**
 * Created by Enny Querales
 */
public abstract class LifecycleLoggingFragment
        extends Fragment {
    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);


    /**
     * Hook method called when a fragment is attached to its hosting activity.
     *
     * @param context Activity context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logger.log("onAttach() - The fragment has been attached to its hosting activity");
    }

    /**
     * Hook method called to do initialization of the fragment. Activity's content view hierarchy
     * may not be initialized at this point.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.log("onCreate() - The fragment is being initialized");
    }

    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.log("onCreateView() - The fragment's view is being created");
        return null;
    }

    /**
     * Hook method called after the fragment's view object has been created.
     *
     * @param savedInstanceState object that contains saved state information.
     * @param view object returned by the inflation process
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logger.log("onViewCreated() - The fragment's view has been created");
    }

    /**
     * Hook method called after the hosting activity has been created and the fragment's user
     * interface has been installed.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.log("onActivityCreated() - The hosting activity has been created");
    }

    /**
     * When the hosting activity is about to become visible it will receive a call to its onStart()
     * method, therefore this method will also be called.
     */
    @Override
    public void onStart() {
        super.onStart();
        logger.log("onStart() - The activity is about to become visible");
    }

    /**
     * When the hosting activity is about to become visible and ready for user interaction Android
     * calls its onResume() method, therefore this method will also be called.
     */
    @Override
    public void onResume() {
        super.onResume();
        logger.log("\"onResume() - The activity has become visible (it is now \"resumed\")");
    }

    /**
     * When the hosting activity is visible, but another activity is about to come in to the
     * foreground, Android calls the its OnPause() method. And this one along.
     */
    @Override
    public void onPause() {
        super.onPause();
        logger.log("onPause() - Another activity might be taking focus (this activity is about to be \"paused\")\"");
    }

    /**
     * When the hosting activity is no longer visible, the hosting activity receives a call to  its
     * OnStop() method. Therefore this method will also be called.
     */
    @Override
    public void onStop() {
        super.onStop();
        logger.log("onStop() - The activity is no longer visible (it is now \"stopped\")");
    }

    /**
     * When the hosting activity is about to be destroyed any fragments that it's hosting also have
     * to be shut down and this happens in several steps. So this is method is called after the view
     * has been detached from the hosting activity.
     * It can be used here to clean up resources associated with the view.
     */
    @Override
    public void onDestroyView() {
        logger.log("onDestroyView() - The view has been detached from the activity ");
        super.onDestroyView();
    }

    /**
     * Hook method called when the fragment is no longer in use.
     * It can be used to release fragment general resources.
     */
    @Override
    public void onDestroy() {
        logger.log("onDestroy() - The fragment is no longer in use and is about to be destroy");

        super.onDestroy();
    }

    /**
     * Hook method called when the fragment is no longer attached to its hosting activity.
     * It could be used to null out references on the fragment's hosting activity.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        logger.log("onDetach() - The fragment has been detached from its hosting activity");
    }
}
