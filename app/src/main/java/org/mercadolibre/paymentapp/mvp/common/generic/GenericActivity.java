package org.mercadolibre.paymentapp.mvp.common.generic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.global.Constants;
import org.mercadolibre.paymentapp.mvp.common.global.Enums.DialogType;
import org.mercadolibre.paymentapp.mvp.common.interfaces.ContextView;
import org.mercadolibre.paymentapp.mvp.common.interfaces.PresenterMethods;
import org.mercadolibre.paymentapp.mvp.common.managers.RetainedFragmentManager;
import org.mercadolibre.paymentapp.mvp.common.utilities.Logger;

/**
 * This class provides a framework for mediating access to an object residing in the Presenter
 * layer in the Model-View-Presenter (MVP) pattern. It automatically handles runtime configuration
 * changes in conjunction with an instance of the Presenter (P), which must implement the
 * PresenterMethods interface.
 * It extends LifecycleLoggingActivity so all lifecycle hook method calls are automatically logged.
 * It also provides access to the Activity and Application contexts in the View layer.
 * <p>
 * The three types used by a GenericActivity are the following:
 * <p>
 * RequiredViewMethods (RVM): the class or interface that defines the methods available to the
 * Presenter object from the View layer.
 * <p>
 * ProvidedPresenterMethods (PPM: the class or interface that defines the methods available to the
 * View layer from the Presenter object.
 * <p>
 * Presenter (P): the class used by the GenericActivity framework to instantiate a Presenter object.
 * <p>
 * Modified by
 *
 * @author Enny Querales
 */
public abstract class GenericActivity<RVM, PPM, P extends PresenterMethods<RVM>>
        extends LifecycleLoggingActivity
        implements ContextView, MVP.RequiredActivityMethods {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private String presenterTAG;
    private Logger logger = new Logger(TAG);
    private P presenter;
    protected Toolbar toolbar = null;
    protected boolean readPermission = false;
    protected boolean writePermission = false;
    protected View dialogView = null;
    protected DialogFragment dialog = null;


    /**
     * Used to retain the ProvidedPresenterMethods state between runtime configuration changes
     */
    private final RetainedFragmentManager retainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);

    /**
     * Initialize or reinitialize the Presenter layer.
     * Handle configuration-related events, including the initial creation of an Activity and any
     * subsequent runtime configuration changes.
     * This must be called after the onCreate(Bundle saveInstanceState) method.
     *
     * @param presenter Class used to create a Presenter object.
     * @param view      Reference to the RequiredViewMethods object.
     */
    public void onCreate(Class<P> presenter, RVM view) {
        presenterTAG = presenter.getSimpleName();
        //noinspection TryWithIdenticalCatches
        try {
            if (retainedFragmentManager.firstTimeIn())
                initialize(presenter, view);
            else
                reinitialize(presenter, view);
        } catch (InstantiationException e) {
            logger.log("onCreate() - " + e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.log("onCreate() - " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the GenericActivity fields.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void initialize(Class<P> presenter, RVM view)
            throws InstantiationException, IllegalAccessException {

        // Create the Presenter object.
        this.presenter = presenter.newInstance();

        // Put instances into the RetainedFragmentManager under a simple name.
        retainedFragmentManager.put(presenterTAG, this.presenter);

        // Calls onCreate hook method on the Presenter layer.
        this.presenter.onCreate(view);
    }

    /**
     * Reinitialize the GenericActivity fields after a runtime configuration change.
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void reinitialize(Class<P> presenter, RVM view)
            throws InstantiationException, IllegalAccessException {

        // Restoring states from the RetainedFragmentManager under a simple name.
        this.presenter = retainedFragmentManager.get(presenterTAG);

        // Checks Presenter state
        if (this.presenter == null)
            initialize(presenter, view);
        else
            this.presenter.onConfigurationChange(view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            switch (requestCode) {
                case Constants.READ_EXTERNAL_STORAGE:
                    readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.WRITE_EXTERNAL_STORAGE:
                    writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Return the ProvidedPresenterMethods instance for use by application logic in the View layer.
     */
    @SuppressWarnings("unchecked")
    public PPM getPresenter() {
        return (PPM) this.presenter;
    }

    /**
     * Return the RetainedFragmentManager.
     */
    public RetainedFragmentManager getRetainedFragmentManager() {
        return retainedFragmentManager;
    }

    /**
     * Toolbar setter
     *
     * @param toolbar Widget view
     */
    @SuppressLint("NewApi")
    public void setToolbar(Toolbar toolbar, boolean isHomeEnable) {
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (isHomeEnable) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
        }
    }

    /**
     * Toolbar getter
     *
     * @return Toolbar
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Return the Activity context.
     */
    @Override
    public Context getActivityContext() {
        return this;
    }

    /**
     * Return the Application context.
     */
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    /**
     * Starts another activity
     *
     * @param activityClass class parameter
     */
    public void startActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    /**
     * Allows fragment placement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment        fragment instance
     */
    public <T extends Fragment> void placeFragment(int containerViewId, T fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * Allows fragment replacement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment        fragment instance
     * @param isStacked       indicates whether fragment should be pushed to the back stack
     */
    public <T extends Fragment> void replaceFragment(int containerViewId, T fragment, boolean isStacked) {
        if (isStacked) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * Replace a view with another
     *
     * @param id          Id of the view that is going to be replaced
     * @param replacement view object replacement
     */
    public void replaceView(int id, View replacement) {
        View view = findViewById(id);
        ViewGroup parent;
        if (view != null) {
            parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeViewAt(index);
            parent.addView(replacement, index);
        } else
            logger.log("replaceView() - Cannot replace, \"id\" was not found for view replacement: "
                    + replacement.getClass().getSimpleName());
    }

    /**
     * This method is used to hide the keyboard after a user has finished typing the url.
     *
     * @param windowToken the token of the window that is making the request, returned by View.getWindowToken()
     */
    public void hideKeyboard(IBinder windowToken) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Display a toast on the screen
     *
     * @param message chosen message pass as resource of type string (e.g. R.string.message)
     * @param length  toast length (e.g. Toast.LENGTH_SHORT)
     */
    @Override
    public void displayToast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    /**
     * Sets a custom dialog view to be displayed on request of the fragment
     */
    public void setDialog(View view) {
        dialogView = view;
    }

    /**
     * Sets a custom dialog fragment to be displayed on request of the fragment
     */
    public void setDialog(DialogFragment dialog) {
        this.dialog = dialog;
    }

    /**
     * Displays dialog with type {@param type}
     *
     * @param type chosen type
     */
    public void displayDialog(DialogType type) {
        switch (type) {
            case TIME_DETERMINED:
                break;
            case TIME_UNDETERMINED:
                LinearLayout progressBar = (LinearLayout) dialogView;
                progressBar.setVisibility(View.VISIBLE);
                break;
            case MESSAGE:
                break;
        }
    }

    /**
     * Dismiss dialog with type {@param type}
     *
     * @param type chosen type
     */
    public void dismissDialog(DialogType type) {
        switch (type) {
            case TIME_DETERMINED:
                break;
            case TIME_UNDETERMINED:
                LinearLayout progressBar = (LinearLayout) dialogView;
                progressBar.setVisibility(View.GONE);
                break;
            case MESSAGE:
                break;
        }
    }

    /**
     * Renders a snack bar at the bottom of the screen
     *
     * @param view    parent view
     * @param message resource ID with desired message
     * @param close   resource ID with desired action title
     */
    public void displaySnackBar(View view, int message, int close, View.OnClickListener listener) {
        Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(close, listener)
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }

    public void showTitleAndMessageDialog(Context context, String title, String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final AlertDialog dialog = alert.setPositiveButton("OK", null).setCancelable(false).setTitle(title).setMessage(message).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });

        dialog.show();
    }

    /**
     * Renders a snack bar at the bottom of the screen
     *
     * @param view    parent view
     * @param message string with desired message
     * @param close   resource ID with desired action title
     */
    public void displaySnackBar(View view, String message, int close, View.OnClickListener listener) {
        Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(close, listener)
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .show();
    }

    /**
     * Update activity view content with new {@param data}
     */
    @Override
    public <T> void updateView(T data) {
        // Implemented on concrete fragment / activity if needed
    }
}
