package org.mercadolibre.paymentapp.mvp.common.managers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.mercadolibre.paymentapp.mvp.common.utilities.Logger;

/**
 * Retains and manages state information between runtime configuration changes of an Activity.
 * Plays the role of the "Originator" in the Memento pattern.
 */
public class RetainedFragmentManager {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);


    /**
     * Name used to identify the RetainedFragment.
     */
    private final String retainedFragmentTag;

    /**
     * WeakReference to the FragmentManager.
     */
    private final WeakReference<FragmentManager> fragmentManager;

    /**
     * Reference to the RetainedFragment.
     */
    private RetainedFragment retainedFragment;

    /**
     * Constructor initializes fields.
     *
     * @param fragmentManager     -
     * @param retainedFragmentTag -
     */
    public RetainedFragmentManager(FragmentManager fragmentManager, String retainedFragmentTag) {
        // Store a WeakReference to the Activity.
        this.fragmentManager = new WeakReference<>(fragmentManager);

        // Store the tag used to identify the RetainedFragment.
        this.retainedFragmentTag = retainedFragmentTag;
    }

    /**
     * Initializes the RetainedFragment the first time it's called.
     *
     * @return true if it's first time the method's been called, else false.
     */
    public boolean firstTimeIn() {
        try {
            // Find the RetainedFragment on Activity restarts.
            // The RetainedFragment has no UI so it must be referenced via a tag.
            retainedFragment = (RetainedFragment) fragmentManager.get().findFragmentByTag(retainedFragmentTag);

            // A value of null means it's the first time in, so it needs to be instantiated.
            if (retainedFragment == null) {
                logger.log("firstTimeIn() - Creating new RetainedFragment with tag: " + retainedFragmentTag);

                // Create a new RetainedFragment.
                retainedFragment = new RetainedFragment();

                // Commit this RetainedFragment to the FragmentManager.
                fragmentManager.get().beginTransaction().add(retainedFragment, retainedFragmentTag).commit();
                return true;
            }
            // A value of non-null means it's not first time in.
            else {
                logger.log("firstTimeIn() - Returning existing RetainedFragment " + retainedFragmentTag);
                return false;
            }
        } catch (NullPointerException e) {
            logger.log("NullPointerException in firstTimeIn()");
            return false;
        }
    }

    /**
     * Add an object with the specified key.
     */
    public void put(String key, Object object) {
        retainedFragment.put(key, object);
    }

    /**
     * Add an object with its class name.
     */
    public void put(Object object) {
        put(object.getClass().getName(), object);
    }

    /**
     * Get an object by its key.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) retainedFragment.get(key);
    }

    /**
     * Return the Activity the RetainedFragment is attached to or null if it's not attached.
     */
    public Activity getActivity() {
        return retainedFragment.getActivity();
    }


    /**
     * "Headless" Fragment that retains state information between configuration changes.
     * Plays the role of the "Memento" in the Memento pattern.
     */
    public static class RetainedFragment
            extends Fragment {

        /**
         * Maps keys to objects.
         */
        private HashMap<String, Object> data = new HashMap<>();

        /**
         * Hook method called when a new Fragment is created.
         *
         * @param savedInstanceState object that contains saved state information.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Ensure the data survives runtime configuration changes.
            setRetainInstance(true);
        }

        /**
         * Add an object with the specified key.
         */
        public void put(String key, Object object) {
            data.put(key, object);
        }

        /**
         * Add an object with its class name.
         */
        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        /**
         * Get the object with specified key.
         */
        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) data.get(key);
        }
    }
}
