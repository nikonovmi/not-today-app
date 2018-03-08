package com.oohdev.oohreminder.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.model.BookModelComplete;
import com.squareup.picasso.Picasso;

import java.util.List;

class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder> {
    private final ContentItemClickResolver mItemClickResolver;
    private final Context mContext;
    private List<BookModelComplete> mBooks;

    BooksRecyclerAdapter(@NonNull Context context, @NonNull List<BookModelComplete> books, @NonNull ContentItemClickResolver clickResolver) {
        mItemClickResolver = clickResolver;
        mContext = context;
        mBooks = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new BooksRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookModelComplete book = mBooks.get(position);
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

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    void addItem(BookModelComplete book) {
        mBooks.add(0, book);
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private final TextView bookTitle;
        private final TextView bookAuthor;
        private final AppCompatImageView bookCover;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookCover = itemView.findViewById(R.id.book_cover);
        }

        @Override
        public boolean onLongClick(View v) {
            return mItemClickResolver.onLongClick(getAdapterPosition());
        }

        @Override
        public void onClick(View v) {
            mItemClickResolver.onClick(getAdapterPosition());
        }
    }
}
