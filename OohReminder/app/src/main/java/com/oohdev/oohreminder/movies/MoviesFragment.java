package com.oohdev.oohreminder.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oohdev.oohreminder.ContentFragment;
import com.oohdev.oohreminder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoviesFragment extends ContentFragment {
    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mRecyclerAdapter;
    private ArrayList<MovieModel> mMovieModels;

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
        MovieModel model1 = new MovieModel();
        model1.title = "Lost Highway";
        model1.description = getResources().getString(R.string.tmpdescription);
        model1.director = "David Lynch";
        MovieModel model2 = new MovieModel();
        model2.title = "Twin Peaks";
        model2.description = getResources().getString(R.string.tmpdescription);
        model2.director = "David Lynch XX";

        mMovieModels = new ArrayList<>(Arrays.asList(model1, model2, model1, model2, model1, model2, model1, model2));
        mRecyclerView = view.findViewById(R.id.movies_recycler);
        mRecyclerAdapter = new MoviesRecyclerAdapter(mMovieModels);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void addElement() {
        Toast.makeText(getContext(), "add movie", Toast.LENGTH_SHORT).show();
    }
}
