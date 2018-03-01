package com.oohdev.oohreminder.movies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oohdev.oohreminder.ContentFragment;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.db.MovieDatabaseHelper;

public class MoviesFragment extends ContentFragment {
    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mRecyclerAdapter;
    private MovieDatabaseHelper mDatabaseHelper;

    public static MoviesFragment newInstance() {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabaseHelper = MovieDatabaseHelper.getInstance(getContext());
        mRecyclerView = view.findViewById(R.id.movies_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new MoviesRecyclerAdapter(mDatabaseHelper.getMovies());
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void addElement() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.add_movie)
                .inputRange(2, 30)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.add_hint, R.string.empty_string, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        new GetMovieInfoTask(input.toString(), "unknown", "no description").execute();
                    }
                }).show();
    }
    private void updateRecycler() {
        mRecyclerAdapter.updateMovies(mDatabaseHelper.getMovies());
    }

    private class GetMovieInfoTask extends AsyncTask<Void, Void, MovieModel> {
        private String title;
        private String director;
        private String description;

        public GetMovieInfoTask(String title, String defDirector, String defDesc) {
            this.title = title;
            this.director = defDirector;
            this.description = defDesc;
        }

        @Override
        protected MovieModel doInBackground(Void... voids) {
            return MovieApiHelper.getMovieModel(title, director, description);
        }

        @Override
        protected void onPostExecute(MovieModel movieModel) {
            mDatabaseHelper.insertMovie(movieModel);
            updateRecycler();
        }
    }
}
