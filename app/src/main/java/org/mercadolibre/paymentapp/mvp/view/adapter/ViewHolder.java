package org.mercadolibre.paymentapp.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.mercadolibre.paymentapp.R;
import org.mercadolibre.paymentapp.mvp.view.custom.CustomTextView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private GenericAdapter.OnRecyclerItemClicked mOnRecyclerItemClicked;

    public CustomTextView textView;
    public ImageView imageView;


    public ViewHolder(View view, GenericAdapter.OnRecyclerItemClicked onRecyclerItemClicked) {
        super(view);
        textView = view.findViewById(R.id.name);
        imageView = view.findViewById(R.id.image);
        mOnRecyclerItemClicked = onRecyclerItemClicked;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnRecyclerItemClicked.onItemClicked(view, getAdapterPosition());
    }
}

