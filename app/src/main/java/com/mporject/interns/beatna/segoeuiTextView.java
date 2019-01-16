package com.mporject.interns.beatna;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class segoeuiTextView extends AppCompatTextView {
    public segoeuiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public segoeuiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public segoeuiTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/segoeui.ttf");
            setTypeface(tf);
        }
    }


}
