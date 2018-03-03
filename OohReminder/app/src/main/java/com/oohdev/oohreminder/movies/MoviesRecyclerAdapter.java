package com.oohdev.oohreminder.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oohdev.oohreminder.R;

import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {
    private final MoviesFragment.MovieItemClickListener mClickListener;
    private List<MovieModel> movieModels;

    public MoviesRecyclerAdapter(List<MovieModel> movieModels, MoviesFragment.MovieItemClickListener longClickListener) {
        this.mClickListener = longClickListener;
        this.movieModels = movieModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel model = movieModels.get(position);
        holder.movieTitle.setText(model.getTitle());
        holder.movieDirector.setText(model.getDirector());
        holder.movieDescription.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public void updateMovies(@NonNull List<MovieModel> newMovies) {
        movieModels.clear();
        movieModels.addAll(newMovies);
        notifyDataSetChanged();
    }

    public List<MovieModel> getItems() {
        return movieModels;
    }

    public void addItem(MovieModel movieModel) {
        movieModels.add(0, movieModel);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        movieModels.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final TextView movieTitle;
        private final TextView movieDirector;
        private final TextView movieDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieDirector = itemView.findViewById(R.id.movie_director);
            movieDescription = itemView.findViewById(R.id.movie_desc);
        }

        @Override
        public boolean onLongClick(View view) {
            return mClickListener.onLongClick(getAdapterPosition());
        }
    }
}
