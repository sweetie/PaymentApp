package org.mercadolibre.paymentapp.mvp.view.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericActivity;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener;
import org.mercadolibre.paymentapp.mvp.common.utilities.Tools;
import org.mercadolibre.paymentapp.mvp.model.data.Data;
import org.mercadolibre.paymentapp.mvp.model.data.ModelPaymentMethod;
import org.mercadolibre.paymentapp.mvp.presenter.PaymentMethodPresenter;
import org.mercadolibre.paymentapp.mvp.view.adapter.EmptyRecyclerView;
import org.mercadolibre.paymentapp.mvp.view.adapter.GenericAdapter;
import org.mercadolibre.paymentapp.mvp.view.adapter.ViewHolder;
import org.mercadolibre.paymentapp.mvp.view.custom.CustomButton;
import org.mercadolibre.paymentapp.mvp.view.decorator.DividerItemDecoration;

import java.util.List;

/**
 * Created by Enny Querales
 */
public class PaymentMethodActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, PaymentMethodPresenter>
        implements MVP.RequiredActivityMethods, Listener.OnNetworkResponseListener, View.OnClickListener {

    /**
     * Attributes
     */
    private static boolean activityStarted;
    private Listener.OnNetworkResponseListener listener;
    private CustomButton customButton;
    private EmptyRecyclerView recyclerView;
    private Data data;
    private ModelPaymentMethod model;

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

        setContentView(R.layout.activity_payment_method);
        listener = this;

        // Instantiate the presenter
        super.onCreate(PaymentMethodPresenter.class, this);

        Intent intent = getIntent();
        data = intent.getParcelableExtra("data");

        // Initialize all view components defined in the activity's layout
        initializeViews();
        Tools.muestraProgressBar(this);
        getPresenter().handleClick(-1, listener, null);
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.payment_method_title));
        customButton = findViewById(R.id.button_next);
        customButton.setOnClickListener(this);

        recyclerView = (EmptyRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setEmptyView(findViewById(R.id.empty_view));
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
                if (model != null)
                    startActivity(new Intent(this, CardIssuerActivity.class).putExtra("data", data));
                break;
        }
    }

    @Override
    public <T> void processResponse(T response) {
        Tools.escondeProgressBar(this);
        final List<ModelPaymentMethod> list = (List<ModelPaymentMethod>) response;
        GenericAdapter<ModelPaymentMethod> adapter = new GenericAdapter<ModelPaymentMethod>(PaymentMethodActivity.this, list) {
            int selectedPosition = -1;

            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent, OnRecyclerItemClicked onRecyclerItemClicked) {
                final View view = LayoutInflater.from(PaymentMethodActivity.this).inflate(R.layout.detail_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(view, onRecyclerItemClicked);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, ModelPaymentMethod val, int position) {
                if (val != null) {
                    if (selectedPosition == position)
                        holder.itemView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    else
                        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                    ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.textView.setText(val.getName());
                    Picasso.with(PaymentMethodActivity.this).load(val.getSecureThumbnail()).into(viewHolder.imageView);
                }
            }

            @Override
            public OnRecyclerItemClicked onGetRecyclerItemClickListener() {
                return new OnRecyclerItemClicked() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        model = list.get(position);
                        data.setPaymentMethod(model.getId());
                        data.setNamePaymentMethod(model.getName());
                    }
                };
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public <T> void processFailure(T response) {
        Tools.escondeProgressBar(this);
        showTitleAndMessageDialog(this, "", (String) response);
    }
}
