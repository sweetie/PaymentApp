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

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.MVP;
import org.mercadolibre.paymentapp.mvp.common.generic.GenericActivity;
import org.mercadolibre.paymentapp.mvp.common.interfaces.Listener;
import org.mercadolibre.paymentapp.mvp.common.utilities.Tools;
import org.mercadolibre.paymentapp.mvp.model.data.Data;
import org.mercadolibre.paymentapp.mvp.model.data.ModelCardIssuer;
import org.mercadolibre.paymentapp.mvp.presenter.CardIssuerPresenter;
import org.mercadolibre.paymentapp.mvp.view.adapter.EmptyRecyclerView;
import org.mercadolibre.paymentapp.mvp.view.adapter.GenericAdapter;
import org.mercadolibre.paymentapp.mvp.view.adapter.ViewHolder;
import org.mercadolibre.paymentapp.mvp.view.custom.CustomButton;
import org.mercadolibre.paymentapp.mvp.view.decorator.DividerItemDecoration;

import java.util.List;

/**
 * Created by Enny Querales
 */
public class CardIssuerActivity
        extends GenericActivity<MVP.RequiredActivityMethods, MVP.ProvidedPresenterMethodsActivity, CardIssuerPresenter>
        implements MVP.RequiredActivityMethods, Listener.OnNetworkResponseListener, View.OnClickListener {

    /**
     * Attributes
     */
    private static boolean activityStarted;
    private Listener.OnNetworkResponseListener listener;
    private CustomButton customButton;
    private EmptyRecyclerView recyclerView;
    private Data data;
    private ModelCardIssuer model;

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

        setContentView(R.layout.activity_card_issuer);
        listener = this;

        // Instantiate the presenter
        super.onCreate(CardIssuerPresenter.class, this);

        Intent intent = getIntent();
        data = intent.getParcelableExtra("data");

        // Initialize all view components defined in the activity's layout
        initializeViews();
        Tools.muestraProgressBar(this);
        getPresenter().handleClick(-1, listener, data);
    }


    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.card_issuer_title));
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
                    startActivity(new Intent(this, InstallmentActivity.class).putExtra("data", data));
                break;
        }
    }

    @Override
    public <T> void processResponse(T response) {
        Tools.escondeProgressBar(this);
        final List<ModelCardIssuer> list = (List<ModelCardIssuer>) response;
        GenericAdapter<ModelCardIssuer> adapter = new GenericAdapter<ModelCardIssuer>(CardIssuerActivity.this, list) {
            int selectedPosition = -1;

            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent, OnRecyclerItemClicked onRecyclerItemClicked) {
                final View view = LayoutInflater.from(CardIssuerActivity.this).inflate(R.layout.detail_item, parent, false);
                ViewHolder viewHolder = new ViewHolder(view, onRecyclerItemClicked);
                return viewHolder;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, ModelCardIssuer val, int position) {
                if (val != null) {
                    if (selectedPosition == position)
                        holder.itemView.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    else
                        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                    ViewHolder viewHolder = (ViewHolder) holder;
                    viewHolder.textView.setText(val.getName());
                }
            }

            @Override
            public OnRecyclerItemClicked onGetRecyclerItemClickListener() {
                return new OnRecyclerItemClicked() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        model = (ModelCardIssuer) list.get(position);
                        data.setCardIssuer(model.getId());
                        data.setNameCardIssuer(model.getName());
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
