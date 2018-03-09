package com.oohdev.oohreminder.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.db.BooksTable;
import com.oohdev.oohreminder.core.model.BookModelComplete;

import junit.framework.Assert;

import java.util.ArrayList;

public class BooksFragment extends ContentFragment {
    private BooksTable mBooksTable;
    private RecyclerView mRecyclerView;
    private BooksRecyclerAdapter mRecyclerAdapter;
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
        mBooksTable = new BooksTable(getContext());
        mItemClickResolver = new BookItemClickResolver();
        mRecyclerView = view.findViewById(R.id.books_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new BooksRecyclerAdapter(getContext(), new ArrayList<BookModelComplete>(), mItemClickResolver);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        updateRecycler();
    }

    @Override
    public void addElement() {
        Assert.assertNotNull(getContext());
        final BookModelComplete book = new BookModelComplete();
        new MaterialDialog.Builder(getContext())
                .title(R.string.add_book)
                .inputRange(2, 30)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.add_title_hint, R.string.empty_string, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        book.setTitle(input.toString());
                        mBooksTable.addBook(book);
                        updateRecycler(book);
                    }
                }).show();
    }

    private void updateRecycler() {
        mRecyclerAdapter.replaceItems(mBooksTable.getBooksOrderedByDate());
    }

    private void updateRecycler(BookModelComplete bookModelComplete) {
        mRecyclerAdapter.addItem(bookModelComplete);
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
