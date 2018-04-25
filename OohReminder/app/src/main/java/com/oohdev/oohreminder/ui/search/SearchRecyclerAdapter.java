package com.oohdev.oohreminder.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.SearchDataObject;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    @NonNull
    private final AdapterOnClickListener mAdapterOnClickListener;
    @NonNull
    private List<SearchDataObject> mItems = new ArrayList<>();
    @NonNull
    private Context mContext;

    SearchRecyclerAdapter(@NonNull Context context, @NonNull AdapterOnClickListener adapterOnClickListener) {
        mContext = context;
        mAdapterOnClickListener = adapterOnClickListener;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new SearchRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchDataObject searchDataObject = mItems.get(position);
        Assert.assertNotNull("Total items = " + mItems.size() + ", pos = " + Integer.toString(position), searchDataObject);
        holder.mPrimaryText.setText(searchDataObject.getPrimaryDescription());
        holder.mSecondaryText.setText(searchDataObject.getSecondaryDescription());
        if (!searchDataObject.getImageUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(searchDataObject.getImageUrl())
                    .error(R.drawable.unknown_book)
                    .into(holder.mSearchImage);
        } else {
            Picasso.with(mContext)
                    .load(searchDataObject.getDefaultImageId())
                    .into(holder.mSearchImage);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void clear() {
        int oldSize = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    void addItems(@NonNull List<SearchDataObject> items) {
        int oldSize = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(oldSize, mItems.size());

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPrimaryText;
        private final TextView mSecondaryText;
        private final AppCompatImageView mSearchImage;

        ViewHolder(View itemView) {
            super(itemView);
            mPrimaryText = itemView.findViewById(R.id.search_card_primary_text);
            mSecondaryText = itemView.findViewById(R.id.search_card_secondary_text);
            mSearchImage = itemView.findViewById(R.id.search_card_image);
            itemView.setOnClickListener(v -> mAdapterOnClickListener.onClick(mItems.get(getAdapterPosition())));
        }
    }

    interface AdapterOnClickListener {
        void onClick(@NonNull SearchDataObject searchDataObject);
    }
}
