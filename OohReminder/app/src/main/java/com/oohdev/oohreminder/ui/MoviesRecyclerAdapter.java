package com.oohdev.oohreminder.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.model.MovieModelComplete;

import java.util.List;

class MoviesRecyclerAdapter extends BasicContentRecyclerAdapter<MovieModelComplete, MoviesRecyclerAdapter.ViewHolder> {
    MoviesRecyclerAdapter(@NonNull List<MovieModelComplete> movies, @Nullable ContentItemClickResolver clickResolver) {
        super(movies, clickResolver);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModelComplete model = mItems.get(position);
        holder.movieTitle.setText(model.getTitle());
        holder.movieDirector.setText(model.getDirector());
        holder.movieDescription.setText(model.getDescription());
    }


    class ViewHolder extends BasicContentRecyclerAdapter.BasicViewHolder {
        private final TextView movieTitle;
        private final TextView movieDirector;
        private final TextView movieDescription;

        ViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieDirector = itemView.findViewById(R.id.movie_director);
            movieDescription = itemView.findViewById(R.id.movie_desc);
        }
    }
}
