package minhson.com.fakemessenger.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 16/8/2017.
 */

public class TextViewRobotoThin extends TextView {
    public TextViewRobotoThin(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewRobotoThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewRobotoThin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Roboto-Thin.ttf", context);
        setTypeface(customFont);
    }
}
