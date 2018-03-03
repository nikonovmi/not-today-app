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

import java.lang.ref.WeakReference;

public class MoviesFragment extends ContentFragment {
    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mRecyclerAdapter;
    private MovieDatabaseHelper mDatabaseHelper;

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
        mDatabaseHelper = MovieDatabaseHelper.getInstance(getContext());
        mRecyclerView = view.findViewById(R.id.movies_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new MoviesRecyclerAdapter(mDatabaseHelper.getMovies());
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecycler();
    }

    @Override
    public void addElement() {
        final MoviesFragment currentFragment = this;
        if (currentFragment.getContext() == null) {
            return;
        }
        new MaterialDialog.Builder(currentFragment.getContext())
                .title(R.string.add_movie)
                .inputRange(2, 30)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.add_hint, R.string.empty_string, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        new GetMovieInfoTask(currentFragment, input.toString(), "unknown", "no mDescription").execute();
                    }
                }).show();
    }

    private void updateRecycler() {
        mRecyclerAdapter.updateMovies(mDatabaseHelper.getMovies());
    }

    // static modifier and WeakReference logic are required to avoid memory leak: goo.gl/hy74u2
    private static class GetMovieInfoTask extends AsyncTask<Void, Void, MovieModel> {
        private final WeakReference<MoviesFragment> moviesFragmentRef;
        private final String mTitle;
        private final String mDirector;
        private final String mDescription;

        GetMovieInfoTask(MoviesFragment moviesFragment, String title, String defDirector, String defDesc) {
            moviesFragmentRef = new WeakReference<>(moviesFragment);
            mTitle = title;
            mDirector = defDirector;
            mDescription = defDesc;
        }

        @Override
        protected MovieModel doInBackground(Void... voids) {
            return MovieApiHelper.getMovieModel(mTitle, mDirector, mDescription);
        }

        @Override
        protected void onPostExecute(MovieModel movieModel) {
            MoviesFragment fragment = moviesFragmentRef.get();
            if (fragment == null || fragment.getContext() == null) {
                return;
            }
            MovieDatabaseHelper.getInstance(fragment.getContext()).insertMovie(movieModel);
            if (fragment.isResumed()) {
                fragment.updateRecycler();
            }
        }
    }
}
