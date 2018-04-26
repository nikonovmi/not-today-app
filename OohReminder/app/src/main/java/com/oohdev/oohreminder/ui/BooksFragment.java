package com.oohdev.oohreminder.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.oohdev.oohreminder.R;
import com.oohdev.oohreminder.core.BookDataObject;
import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.core.api.books.BookSearchProvider;
import com.oohdev.oohreminder.core.db.BooksTable;
import com.oohdev.oohreminder.ui.search.SearchActivity;

import junit.framework.Assert;

import java.io.Serializable;
import java.util.ArrayList;

public class BooksFragment extends ContentFragment {
    private static final int BOOKS_FRAGMENT_REQUEST_CODE = 2;
    private BooksTable mBooksTable;
    private RecyclerView mRecyclerView;
    private BooksRecyclerAdapter mRecyclerAdapter;

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
        mRecyclerView = view.findViewById(R.id.books_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BookItemClickResolver bookItemClickResolver = new BookItemClickResolver();
        mRecyclerAdapter = new BooksRecyclerAdapter(getContext(), new ArrayList<>(), bookItemClickResolver);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        updateRecycler();
    }

    @Override
    public void addElement() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivityForResult(intent, BOOKS_FRAGMENT_REQUEST_CODE);
    }

    @NonNull
    @Override
    SearchProvider getSearchProvider() {
        return new BookSearchProvider();
    }

    private void updateRecycler() {
        mRecyclerAdapter.replaceItems(mBooksTable.getBooksOrderedByDate());
    }

    private void updateRecycler(BookDataObject book) {
        mRecyclerAdapter.addItem(book);
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BOOKS_FRAGMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Serializable searchResult = data.getSerializableExtra(SearchActivity.SEARCH_RESULT_KEY);
                if (searchResult != null && BookDataObject.class.isInstance(searchResult)) {
                    BookDataObject bookDataObject = (BookDataObject) searchResult;
                    mBooksTable.addBook(bookDataObject);
                    updateRecycler(bookDataObject);
                }
            }
        }
    }

    private class BookItemClickResolver implements ContentItemClickResolver {

        @Override
        public boolean onLongClick(final int item) {
            final String itemTitle = mRecyclerAdapter.getItems().get(item).getTitle();
            Assert.assertNotNull(getContext());
            new MaterialDialog.Builder(getContext())
                    .title(R.string.delete_book)
                    .content(R.string.sure_to_delete_book)
                    .positiveText(R.string.delete)
                    .negativeText(R.string.cancel)
                    .onPositive((dialog, which) -> {
                        mRecyclerAdapter.removeItem(item);
                        mBooksTable.removeBook(itemTitle);
                    }).show();
            return true;
        }

        @Override
        public void onClick(int item) {
        }
    }
}
