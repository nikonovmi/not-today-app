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
import com.oohdev.oohreminder.core.model.BookModelComplete;
import com.squareup.picasso.Picasso;

import java.util.List;

class BooksRecyclerAdapter extends BasicContentRecyclerAdapter<BookModelComplete, BooksRecyclerAdapter.ViewHolder> {
    private final Context mContext;

    BooksRecyclerAdapter(@NonNull Context context, @NonNull List<BookModelComplete> books, @Nullable ContentItemClickResolver clickResolver) {
        super(books, clickResolver);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new BooksRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookModelComplete book = mItems.get(position);
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookTitle.setText(book.getTitle());
        if (!book.getCoverUrl().isEmpty()) {
            Picasso.with(mContext)
                    .load(book.getCoverUrl())
                    .error(R.drawable.unknown_book)
                    .into(holder.bookCover);
        } else {
            Picasso.with(mContext)
                    .load(R.drawable.unknown_book)
                    .into(holder.bookCover);
        }

    }

    class ViewHolder extends BasicContentRecyclerAdapter.BasicViewHolder {
        private final TextView bookTitle;
        private final TextView bookAuthor;
        private final AppCompatImageView bookCover;

        ViewHolder(View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookCover = itemView.findViewById(R.id.book_cover);
        }
    }

}
