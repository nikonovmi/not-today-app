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
import com.oohdev.oohreminder.core.api.search.SearchDataObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    @NonNull
    private List<SearchDataObject> mItems = new ArrayList<>();
    @NonNull
    private Context mContext;

    public SearchRecyclerAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new SearchRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchDataObject searchDataObject = mItems.get(position);
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

    public void clear() {
        int oldSize = mItems.size();
        mItems.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public void addItems(@NonNull List<SearchDataObject> items) {
        int oldSize = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(oldSize, mItems.size());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPrimaryText;
        private final TextView mSecondaryText;
        private final AppCompatImageView mSearchImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mPrimaryText = itemView.findViewById(R.id.search_card_primary_text);
            mSecondaryText = itemView.findViewById(R.id.search_card_secondary_text);
            mSearchImage = itemView.findViewById(R.id.search_card_image);
        }
    }
}
