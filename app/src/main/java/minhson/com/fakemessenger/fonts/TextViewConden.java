package minhson.com.fakemessenger.fonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by binhnk on 6/14/2017.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewConden extends TextView{
    public TextViewConden(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextViewConden(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextViewConden(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Roboto-Condensed.ttf", context);
        setTypeface(customFont);
    }
}
