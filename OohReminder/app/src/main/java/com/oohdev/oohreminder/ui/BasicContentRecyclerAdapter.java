package com.oohdev.oohreminder.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

abstract class BasicContentRecyclerAdapter<T, S extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<S> {
    @Nullable
    protected final ContentItemClickResolver mItemClickResolver;
    @NonNull
    protected List<T> mItems;

    BasicContentRecyclerAdapter(@NonNull List<T> items, @Nullable ContentItemClickResolver clickResolver) {
        mItemClickResolver = clickResolver;
        mItems = items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void replaceItems(@NonNull List<T> newItems) {
        mItems.clear();
        mItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    List<T> getItems() {
        return mItems;
    }

    void addItem(@NonNull T item) {
        mItems.add(0, item);
        notifyItemInserted(0);
    }

    void removeItem(int position) {
        if (position >= mItems.size()) {
            throw new IndexOutOfBoundsException("IOFB while removing item from BasicContentRecyclerAdapter");
        }
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        public BasicViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickResolver != null) {
                mItemClickResolver.onClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemClickResolver != null) {
                return mItemClickResolver.onLongClick(getAdapterPosition());
            }
            return false;
        }
    }
}
