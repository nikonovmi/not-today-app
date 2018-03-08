package com.oohdev.oohreminder.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.model.BookModelComplete;

import junit.framework.Assert;

import java.util.ArrayList;

public class BooksFragment extends ContentFragment {
    private RecyclerView mRecyclerView;
    private BooksRecyclerAdapter mBookAdapter;
    private BookItemClickResolver mItemClickResolver;

    public static BooksFragment newInstance() {
        Bundle args = new Bundle();
        BooksFragment fragment = new BooksFragment();
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
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Assert.assertNotNull(getContext());
        mItemClickResolver = new BookItemClickResolver();
        mRecyclerView = view.findViewById(R.id.books_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBookAdapter = new BooksRecyclerAdapter(getContext(), new ArrayList<BookModelComplete>(), mItemClickResolver);
        mRecyclerView.setAdapter(mBookAdapter);
    }

    @Override
    public void addElement() {
        BookModelComplete bookModelComplete = new BookModelComplete();
        bookModelComplete.setTitle("Trainspotting");
        bookModelComplete.setAuthor("Irvine Welsh");
        bookModelComplete.setCoverUrl("");
        updateRecycler(bookModelComplete);
    }

    private void updateRecycler(BookModelComplete bookModelComplete) {
        mBookAdapter.addItem(bookModelComplete);
        mRecyclerView.scrollToPosition(0);
    }

    private class BookItemClickResolver implements ContentItemClickResolver {

        @Override
        public boolean onLongClick(int item) {
            return false;
        }

        @Override
        public void onClick(int item) {
        }
    }
}
