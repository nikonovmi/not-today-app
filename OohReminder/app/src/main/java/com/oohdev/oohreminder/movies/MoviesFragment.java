package com.oohdev.oohreminder.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oohdev.oohreminder.ContentFragment;
import com.oohdev.oohreminder.R;

public class MoviesFragment extends ContentFragment {
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
    public void addElement() {
        Toast.makeText(getContext(), "add movie", Toast.LENGTH_SHORT).show();
    }
}
