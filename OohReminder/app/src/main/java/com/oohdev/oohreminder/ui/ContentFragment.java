package com.oohdev.oohreminder.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.oohdev.oohreminder.core.api.search.SearchProvider;

public abstract class ContentFragment extends Fragment {
    abstract void addElement();

    @NonNull
    abstract SearchProvider getSearchProvider();
}
