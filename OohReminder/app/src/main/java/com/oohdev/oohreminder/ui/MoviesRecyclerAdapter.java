package com.oohdev.oohreminder.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.core.model.MovieModelComplete;
import com.oohdev.oohreminder.R;

import java.util.List;

class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {
    private final ContentItemClickResolver mItemClickResolver;
    private List<MovieModelComplete> mMovies;

    MoviesRecyclerAdapter(List<MovieModelComplete> movies, ContentItemClickResolver longClickListener) {
        mItemClickResolver = longClickListener;
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModelComplete model = mMovies.get(position);
        holder.movieTitle.setText(model.getTitle());
        holder.movieDirector.setText(model.getDirector());
        holder.movieDescription.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    void updateMovies(@NonNull List<MovieModelComplete> newMovies) {
        mMovies.clear();
        mMovies.addAll(newMovies);
        notifyDataSetChanged();
    }

    List<MovieModelComplete> getItems() {
        return mMovies;
    }

    void addItem(MovieModelComplete movie) {
        mMovies.add(0, movie);
        notifyItemInserted(0);
    }

    void removeItem(int position) {
        mMovies.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private final TextView movieTitle;
        private final TextView movieDirector;
        private final TextView movieDescription;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieDirector = itemView.findViewById(R.id.movie_director);
            movieDescription = itemView.findViewById(R.id.movie_desc);
        }

        @Override
        public boolean onLongClick(View view) {
            return mItemClickResolver.onLongClick(getAdapterPosition());
        }

        @Override
        public void onClick(View v) {
            mItemClickResolver.onClick(getAdapterPosition());
        }
    }
}
