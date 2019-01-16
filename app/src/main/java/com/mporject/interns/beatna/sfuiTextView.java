package com.mporject.interns.beatna;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class sfuiTextView extends AppCompatTextView {
    public sfuiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public sfuiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public sfuiTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/sf_ui_display_bold_58646a511e3d9.otf");
            setTypeface(tf);
        }
    }
}
