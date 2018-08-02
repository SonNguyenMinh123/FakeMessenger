package minhson.com.fakemessenger.item;

import java.io.Serializable;

/**
 * Created by Administrator on 22/7/2017.
 */

public class ChatMessage implements Serializable {
    public int id;
    public int position;
    public String body;
    public String isMe;
    public String uriAvatar;
    public int color;
    private int type;
    private String sticker;
    private int like;

    public ChatMessage() {

    }

    public ChatMessage(int position, String body, String isMe, String sticker) {
        this.position = position;
        this.body = body;
        this.isMe = isMe;
        this.sticker = sticker;
    }

    public ChatMessage(int position, String uriAvatar) {
        this.position = position;
        this.uriAvatar = uriAvatar;
    }

    public ChatMessage(int position, String isMe, String uriAvatar, int color, int type) {
        this.position = position;
        this.isMe = isMe;
        this.uriAvatar = uriAvatar;
        this.color = color;
        this.type = type;
    }

    public ChatMessage(int position, String isMe, String uriAvatar, int color, int type, String sticker) {
        this.position = position;
        this.isMe = isMe;
        this.uriAvatar = uriAvatar;
        this.color = color;
        this.type = type;
        this.sticker = sticker;
    }

    public ChatMessage(int position, String body, String isMe, String uriAvatar, int color, int type) {
        this.position = position;
        this.body = body;
        this.isMe = isMe;
        this.uriAvatar = uriAvatar;
        this.color = color;
        this.type = type;
    }

    public ChatMessage(int id, int position, String body,
                       String isMe, String uriAvatar, int color, int type, String sticker, int like) {
        this.id = id;
        this.position = position;
        this.body = body;
        this.isMe = isMe;
        this.uriAvatar = uriAvatar;
        this.color = color;
        this.type = type;
        this.sticker = sticker;
        this.like = like;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIsMe() {
        return isMe;
    }

    public void setIsMe(String isMe) {
        this.isMe = isMe;
    }

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
