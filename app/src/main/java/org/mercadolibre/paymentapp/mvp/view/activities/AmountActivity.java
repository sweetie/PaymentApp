package org.mercadolibre.paymentapp.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericActivity;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener.ExecutorListener;
import org.mercadolibre.paymentapp.mvp.model.data.Data;
import org.mercadolibre.paymentapp.mvp.presenter.PaymentMethodPresenter;
import org.mercadolibre.paymentapp.mvp.view.custom.CustomButton;

/**
 * Created by Enny Querales
 */
public class AmountActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, PaymentMethodPresenter>
        implements MVP.RequiredActivityMethods, View.OnClickListener, ExecutorListener {

    /**
     * Attributes
     */
    private static boolean activityStarted;
    private CustomButton customButton;
    private EditText editTextAmount;
    private Data data;

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

        setContentView(R.layout.activity_amount);

        // Instantiate the presenter
        super.onCreate(PaymentMethodPresenter.class, this);


        // Initialize all view components defined in the activity's layout
        initializeViews();
    }


    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.amount_title));
        setSupportActionBar(toolbar);
        customButton = findViewById(R.id.button_next);
        customButton.setOnClickListener(this);
        editTextAmount = findViewById(R.id.input_amount);
        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches("^(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                    editTextAmount.removeTextChangedListener(this);
                    editTextAmount.setText(cashAmountBuilder.toString());

                    editTextAmount.setTextKeepState("$" + cashAmountBuilder.toString());
                    Selection.setSelection(editTextAmount.getText(), cashAmountBuilder.toString().length() + 1);

                    editTextAmount.addTextChangedListener(this);
                }
            }
        });
    }

    private int validateForm() {
        int errors = 0;

        String monto = editTextAmount.getText().toString().trim().replace("$", "");
        Double importe = 0.0;

        if (monto.isEmpty()) {
            editTextAmount.setError(getResources().getString(R.string.error_general_campoVacio));
            errors++;
        } else {
            importe = Double.parseDouble(monto);
            if (importe <= 0) {
                errors++;
                editTextAmount.setError(getResources().getString(R.string.error_operacion_importeCero));
            } else {
                editTextAmount.setError(null);
            }
        }
        return errors;
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                int errors = validateForm();
                if (errors == 0) {
                    data = new Data();
                    data.setAmount(editTextAmount.getText().toString().replace("$", ""));
                    startActivity(new Intent(this, PaymentMethodActivity.class).putExtra("data", data));
                }
                break;
        }
    }

    @Override
    public void execute(String name) {
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        data = intent.getParcelableExtra("data");

        if (data != null) {
            showTitleAndMessageDialog(this, "Datos de la Transacción",
                    "Monto: " + data.getAmount() + "" +
                            "\n\nMedio de Pago: " + data.getNamePaymentMethod() +
                            "\n\nBanco: " + data.getNameCardIssuer() +
                            "\n\nCuotas: " + data.getDues());
        }
    }
}
