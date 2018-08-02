package minhson.com.fakemessenger.item;

/**
 * Created by Administrator on 22/7/2017.
 */

public class ItemFakeChat {
    private int id;
    private String avatar;
    private String contact;
    private String messenger;
    private String date;

    public ItemFakeChat(int id, String avatar) {
        this.id = id;
        this.avatar = avatar;
    }

    public ItemFakeChat(int id, String avatar, String contact, String messenger, String date) {
        this.id = id;
        this.avatar = avatar;
        this.contact = contact;
        this.messenger = messenger;
        this.date = date;

    }

    public ItemFakeChat(String avatar, String contact, String messenger, String date) {
        this.avatar = avatar;
        this.contact = contact;
        this.messenger = messenger;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
