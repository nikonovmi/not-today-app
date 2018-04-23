package com.oohdev.oohreminder.ui.search;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.oohdev.oohreminder.R;

public class SearchBarView extends LinearLayout {
    private AppCompatImageView mBackIcon;
    private AppCompatImageView mClearIcon;
    private EditText mInput;

    public SearchBarView(Context context) {
        super(context);
        init(context, null);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);
        @DrawableRes int backIconId = R.drawable.ic_search_back;
        @DrawableRes int clearIconId = R.drawable.ic_search_clear;
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.SearchBarView, 0, 0);
            try {
                backIconId = typedArray.getResourceId(R.styleable.SearchBarView_backIcon, backIconId);
                clearIconId = typedArray.getResourceId(R.styleable.SearchBarView_clearIcon, clearIconId);
            } finally {
                typedArray.recycle();
            }
        }
        LayoutInflater.from(context).inflate(R.layout.view_search_bar, this);
        mBackIcon = this.findViewById(R.id.search_bar_back_icon);
        mBackIcon.setImageResource(backIconId);
        mClearIcon = this.findViewById(R.id.search_bar_clear_icon);
        mClearIcon.setImageResource(clearIconId);
        mInput = this.findViewById(R.id.search_bar_input);
    }

    public void setOnBackClickListener(@Nullable View.OnClickListener clickListener) {
        mBackIcon.setOnClickListener(clickListener);
    }

    public void setOnClearClickListener(@Nullable View.OnClickListener clickListener) {
        mClearIcon.setOnClickListener(clickListener);
    }
}
