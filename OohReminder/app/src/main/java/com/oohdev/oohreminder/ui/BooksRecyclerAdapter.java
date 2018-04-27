package com.oohdev.oohreminder.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.BookDataObject;
import com.squareup.picasso.Picasso;

import java.util.List;

class BooksRecyclerAdapter extends BasicContentRecyclerAdapter<BookDataObject, BooksRecyclerAdapter.ViewHolder> {
    private final Context mContext;

    BooksRecyclerAdapter(@NonNull Context context, @NonNull List<BookDataObject> books, @Nullable ContentItemClickResolver clickResolver) {
        super(books, clickResolver);
        mContext = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new BooksRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookDataObject book = mItems.get(position);
        holder.mBookAuthor.setText(book.getAuthor());
        holder.mBookTitle.setText(book.getTitle());
        if (!book.getCoverUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(book.getCoverUrl())
                    .error(R.drawable.unknown_book)
                    .into(holder.mBookCover);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.unknown_book)
                    .into(holder.mBookCover);
        }

    }

    class ViewHolder extends BasicContentRecyclerAdapter.BasicViewHolder {
        private final TextView mBookTitle;
        private final TextView mBookAuthor;
        private final AppCompatImageView mBookCover;

        ViewHolder(View itemView) {
            super(itemView);
            mBookTitle = itemView.findViewById(R.id.book_title);
            mBookAuthor = itemView.findViewById(R.id.book_author);
            mBookCover = itemView.findViewById(R.id.book_cover);
        }
    }

}
