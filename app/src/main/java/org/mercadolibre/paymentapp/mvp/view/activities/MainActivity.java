package org.mercadolibre.paymentapp.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericActivity;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener.ExecutorListener;
import org.mercadolibre.paymentapp.mvp.presenter.PaymentMethodPresenter;

/**
 * Created by Enny Querales
 */
public class MainActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, PaymentMethodPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener, ExecutorListener {

    /**
     * Attributes
     */
    private static boolean activityStarted;

    /**
     * Vector drawable support
     */
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (activityStarted && getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) != 0) {
            finish();
            return;
        }

        activityStarted = true;

        setContentView(R.layout.activity_main);

        // Instantiate the presenter
        super.onCreate(PaymentMethodPresenter.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }


    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {

    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        // no-op
    }

    @Override
    public void execute(String name) {
    }
}
