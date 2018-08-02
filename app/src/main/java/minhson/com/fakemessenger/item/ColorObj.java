package minhson.com.fakemessenger.item;

import java.io.Serializable;

/**
 * Created by Administrator on 2/8/2017.
 */

public class ColorObj implements Serializable{
    private String body;
    private String uri;
    private String sticker;
    private int like;

    public ColorObj(String body, String uri, String sticker, int like) {
        this.body = body;
        this.uri = uri;
        this.sticker = sticker;
        this.like = like;
    }
}
