package org.mercadolibre.paymentapp.mvp.common.generic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.mercadolibre.paymentapp.mvp.common.utilities.Logger;

/**
 * This abstract class extends the Activity class and overrides lifecycle callbacks for logging
 * various lifecycle events.
 *
 * Modified by
 * @author Enny Querales
 */
public abstract class LifecycleLoggingActivity
        extends AppCompatActivity {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);


    /**
     * Hook method called when a new instance of Activity is created.
     * 
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            // The activity is being re-created. Use the savedInstanceState bundle for initializations
            // either during onCreate() or onRestoreInstanceState().
            logger.log("onCreate() - Activity re-created");
        else
            // Activity is being created anew. No prior saved instance state available in Bundle object.
            logger.log("onCreate() - Activity created anew");
    }

    /**
     * Hook method called after onCreate() or after onRestart() (when the activity is being restarted
     * from stopped state). Should re-acquire resources relinquished when activity was stopped
     * onStop()) or acquire those resources for the first time after onCreate().
     */
    @Override
	protected void onStart() {
        super.onStart();
        logger.log("onStart() - The activity is about to become visible");
    }

    /**
     * Hook method called after onRestoreStateInstance(Bundle) only if there is a prior saved instance
     * state in Bundle object or is called immediately after onStart() otherwise.
     * User can begin interacting with activity.
     *
     * Note:
     * Place to start animations, acquire exclusive resources, such as the camera.
     */
    @Override
	protected void onResume() {
        super.onResume();
        logger.log("onResume() - The activity has become visible (it is now \"resumed\")");
    }

    /**
     * Hook method called when an Activity loses focus but is still visible in background.
     * May be followed by onStop() or onResume().
     * Often used to release exclusive resources such as the camera.
     *
     * Note:
     * Delegate more CPU intensive operation to onStop for seamless transition to next activity.
     * Save persistent state (onSaveInstanceState()) in case app is killed.
     */
    @Override
	protected void onPause() {
        super.onPause();
        logger.log("onPause() - Another activity might be taking focus (this activity is about to be \"paused\")");
    }

    /**
     * Called when Activity is no longer visible. Release resources that may cause memory leak.
     * Save instance state (onSaveInstanceState()) in case activity is killed.
     */
    @Override
	protected void onStop() {
        super.onStop();
        logger.log("onStop() - The activity is no longer visible (it is now \"stopped\")");
    }

    /**
     * Hook method called when user restarts a stopped activity.
     * Is followed by a call to onStart() and onResume().
     */
    @Override
	protected void onRestart() {
        super.onRestart();
        logger.log("onRestart() - The activity is about to be restarted");
    }

    /**
     * Hook method that gives a final chance to release resources and stop spawned threads.
     * onDestroy() may not always be called; e.g. when system kills hosting process
     */
    @Override
	protected void onDestroy() {
        super.onDestroy();
        logger.log("onDestroy() - The activity is about to be destroyed");
    }
}
