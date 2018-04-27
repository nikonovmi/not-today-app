package com.oohdev.oohreminder.ui;

import android.app.Application;
import android.support.annotation.NonNull;

import com.oohdev.oohreminder.core.api.SearchProvider;
import com.oohdev.oohreminder.core.api.dagger.SearchModule;
import com.oohdev.oohreminder.ui.search.dagger.DaggerSearchComponent;
import com.oohdev.oohreminder.ui.search.dagger.SearchComponent;

public class App extends Application {
    //TODO add Context component
    protected static App instance;

    private SearchComponent mSearchComponent;

    public static App get() {
        return instance;
    }

    public SearchComponent getSearchComponent() {
        return mSearchComponent;
    }

    void setSearchProvider(@NonNull SearchProvider searchProvider) {
        mSearchComponent = DaggerSearchComponent.builder()
                .searchModule(new SearchModule(searchProvider))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
