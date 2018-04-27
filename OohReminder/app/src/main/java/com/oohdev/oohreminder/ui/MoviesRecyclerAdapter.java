package com.oohdev.oohreminder.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.MovieDataObject;

import java.util.List;

class MoviesRecyclerAdapter extends BasicContentRecyclerAdapter<MovieDataObject, MoviesRecyclerAdapter.ViewHolder> {
    MoviesRecyclerAdapter(@NonNull List<MovieDataObject> movies, @Nullable ContentItemClickResolver clickResolver) {
        super(movies, clickResolver);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDataObject movie = mItems.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieDirector.setText(movie.getDirector());
        holder.movieDescription.setText(movie.getDescription());
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
