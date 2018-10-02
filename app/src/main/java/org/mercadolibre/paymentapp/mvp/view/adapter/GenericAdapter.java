package org.mercadolibre.paymentapp.mvp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> items;
    private OnRecyclerItemClicked onRecyclerItemClicked;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent , OnRecyclerItemClicked onRecyclerItemClicked);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val, int position);

    public abstract OnRecyclerItemClicked onGetRecyclerItemClickListener();

    public GenericAdapter(Context context, List<T> items){
        this.context = context;
        this.items = items;
        onRecyclerItemClicked = onGetRecyclerItemClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = setViewHolder(parent , onRecyclerItemClicked);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder,items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<T> savedCardItemz){
        items = savedCardItemz;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return items.get(position);
    }

    public interface OnRecyclerItemClicked{
        void onItemClicked(View view, int position);
    }

    public void clearSelections() {
        items.clear();
        this.notifyDataSetChanged();
    }
}
