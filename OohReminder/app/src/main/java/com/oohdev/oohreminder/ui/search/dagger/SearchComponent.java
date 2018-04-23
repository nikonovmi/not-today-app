package com.oohdev.oohreminder.ui.search.dagger;


import com.oohdev.oohreminder.core.api.search.dagger.SearchModule;
import com.oohdev.oohreminder.ui.search.SearchActivity;

import dagger.Component;

@Component(modules = {SearchModule.class})
public interface SearchComponent {
    void inject(SearchActivity searchActivity);
}
