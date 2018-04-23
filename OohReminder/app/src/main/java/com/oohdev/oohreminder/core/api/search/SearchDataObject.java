package com.oohdev.oohreminder.core.api.search;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.io.Serializable;

public abstract class SearchDataObject implements Serializable {

    @NonNull
    public abstract String getPrimaryDescription();

    @NonNull
    public abstract String getSecondaryDescription();

    @NonNull
    public abstract String getImageUrl();

    @DrawableRes
    public abstract int getDefaultImageId();
}
