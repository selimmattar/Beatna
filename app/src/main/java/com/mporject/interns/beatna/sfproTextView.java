package com.mporject.interns.beatna;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class sfproTextView extends AppCompatTextView {
    public sfproTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public sfproTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public sfproTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/sf_pro_display_regular.ttf");
            setTypeface(tf);
        }
    }
}
