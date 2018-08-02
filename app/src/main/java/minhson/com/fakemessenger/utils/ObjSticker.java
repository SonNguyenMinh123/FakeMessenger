package minhson.com.fakemessenger.utils;

import android.support.annotation.NonNull;

/**
 * Created by Administrator on 8/8/2017.
 */

public class ObjSticker implements Comparable {
    private int drawableSticker;

    public ObjSticker(int drawableSticker) {
        this.drawableSticker = drawableSticker;
    }

    public int getDrawableSticker() {
        return drawableSticker;
    }

    public void setDrawableSticker(int drawableSticker) {
        this.drawableSticker = drawableSticker;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if (((ObjSticker) another).getDrawableSticker() > drawableSticker) {
            return 1;
        }
        if (((ObjSticker) another).getDrawableSticker() == drawableSticker) {
            return 0;
        } else {
            return -1;
        }

    }
}
