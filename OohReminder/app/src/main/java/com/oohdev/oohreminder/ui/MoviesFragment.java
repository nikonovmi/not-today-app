package com.oohdev.oohreminder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.api.movies.MovieSearchProvider;
import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.core.db.MoviesTable;
import com.oohdev.oohreminder.core.MovieDataObject;
import com.oohdev.oohreminder.ui.search.SearchActivity;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import java.io.Serializable;

public class MoviesFragment extends ContentFragment {
    private static final int MOVIES_FRAGMENT_REQUEST_CODE = 1;

    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mRecyclerAdapter;
    private MoviesTable mMoviesTable;

    public static MoviesFragment newInstance() {
        // Bundle logic might be useful in future
        Bundle args = new Bundle();
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Assert.assertNotNull(getContext());
        mMoviesTable = new MoviesTable(getContext());
        mRecyclerView = view.findViewById(R.id.movies_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieItemClickListener movieItemClickListener = new MovieItemClickListener();
        mRecyclerAdapter = new MoviesRecyclerAdapter(mMoviesTable.getMoviesOrderedByDate(), movieItemClickListener);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecycler();
    }

    @Override
    public void addElement() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivityForResult(intent, MOVIES_FRAGMENT_REQUEST_CODE);
    }

    @NonNull
    @Override
    SearchProvider getSearchProvider() {
        return new MovieSearchProvider();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOVIES_FRAGMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Serializable searchResult = data.getSerializableExtra(SearchActivity.SEARCH_RESULT_KEY);
                if (searchResult != null && MovieDataObject.class.isInstance(searchResult)) {
                    MovieDataObject movieDataObject = (MovieDataObject) searchResult;
                    mMoviesTable.insertMovie(movieDataObject);
                    updateRecycler(movieDataObject);
                }
            }
        }
    }

    private void updateRecycler() {
        mRecyclerAdapter.replaceItems(mMoviesTable.getMoviesOrderedByDate());
    }

    private void updateRecycler(@NonNull MovieDataObject movie) {
        mRecyclerAdapter.addItem(movie);
        mRecyclerView.scrollToPosition(0);
    }

    private class MovieItemClickListener implements ContentItemClickResolver {
        @Override
        public boolean onLongClick(final int item) {
            final String itemTitle = mRecyclerAdapter.getItems().get(item).getTitle();
            Assert.assertNotNull(getContext());
            new MaterialDialog.Builder(getContext())
                    .title(R.string.delete_movie)
                    .content(R.string.sure_to_delete_movie)
                    .positiveText(R.string.delete)
                    .negativeText(R.string.cancel)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            mRecyclerAdapter.removeItem(item);
                            mMoviesTable.removeMovie(itemTitle);
                        }
                    }).show();
            return true;
        }

        @Override
        public void onClick(int item) {
            final MovieDataObject movie = mRecyclerAdapter.getItems().get(item);
            Assert.assertNotNull(getContext());
            MaterialDialog completeInfoDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.movie_complete_desc)
                    .customView(R.layout.movie_card_complete, true)
                    .positiveText(R.string.ok)
                    .build();
            View movieCardComplete = completeInfoDialog.getCustomView();
            TextView title = movieCardComplete.findViewById(R.id.movie_title_complete);
            title.setText(movie.getTitle());
            TextView desc = movieCardComplete.findViewById(R.id.movie_desc_complete);
            desc.setText(movie.getDescription());
            TextView director = movieCardComplete.findViewById(R.id.movie_director_complete);
            director.setText(movie.getDirector());
            AppCompatImageView poster = movieCardComplete.findViewById(R.id.movie_poster);
            if (!movie.getPosterUrl().isEmpty()) {
                Picasso.with(getContext())
                        .load(movie.getPosterUrl())
                        .error(movie.getDefaultImageId())
                        .into(poster);
            } else {
                Picasso.with(getContext())
                        .load(movie.getDefaultImageId())
                        .into(poster);
            }
            completeInfoDialog.show();
        }
    }
}
