package minhson.com.fakemessenger.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import minhson.com.fakemessenger.item.ChatMessage;

/**
 * Created by Administrator on 31/7/2017.
 */

public class SaveMessengerDataBaseMgr {
    private static final String DATABASE_PATH = Environment.getDataDirectory().getAbsolutePath()
            + "/data/minhson.com.fakemessenger/";
    private static final String DATABASE_NAME = "messenger.sqlite";

    private static final String TABLE_NOTE = "message";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_BODY = "body";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_AVATAR = "uriAvatar";
    private static final String COLUMN_IS_ME = "isMe";
    private static final String COLUMN_STICKER = "sticker";
    private static final String COLUMN_LIKE = "like";

    private SQLiteDatabase sqLiteDatabase;

    public void copyDatabase(Context context) {
        //mo file de doc
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = null;
            try {
                is = assetManager.open(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //mo file de ghi
            String path = DATABASE_PATH + DATABASE_NAME;
            File f = new File(path);
            if (f.exists()) {
                return;
            } else {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            byte[] b = new byte[1024];
            int lenght = 0;

            while ((lenght = is.read(b)) != -1) {
                fos.write(b, 0, lenght);
                fos.flush();
            }
            is.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    public void closeDataBase() {
        if (sqLiteDatabase != null || sqLiteDatabase.isOpen()) {//neu no chua co gi va dang duoc mo se dc dong
            sqLiteDatabase.close();
        }
    }

    public boolean insertChat(ChatMessage chatMessage) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POSITION, chatMessage.getPosition());
        values.put(COLUMN_BODY, chatMessage.getBody());
        values.put(COLUMN_IS_ME, chatMessage.getIsMe());
        values.put(COLUMN_AVATAR, chatMessage.getUriAvatar());
        values.put(COLUMN_COLOR, chatMessage.getColor());
        values.put(COLUMN_TYPE, chatMessage.getType());

        long id = sqLiteDatabase.insert(TABLE_NOTE, null, values);
        closeDataBase();
        return true;
    }

    public boolean insertColor(ChatMessage chatMessage) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POSITION, chatMessage.getPosition());
        values.put(COLUMN_COLOR, chatMessage.getColor());

        long id = sqLiteDatabase.insert(TABLE_NOTE, null, values);
        closeDataBase();
        return true;
    }

//    public ChatMessage getColor(int id) {
//        openDatabase();
//        Cursor cursor = sqLiteDatabase.query(TABLE_NOTE, new String[]{COLUMN_POSITION, COLUMN_ID,
//                        COLUMN_COLOR}, COLUMN_POSITION + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null )
//            cursor.moveToFirst();
//
//        ChatMessage message = new ChatMessage(Integer.parseInt(cursor.getString(1))
//                ,cursor.getString(3));
//        // return contact
//        closeDataBase();
//        return message;
//
//    }

    public boolean insertImage(ChatMessage chatMessage) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POSITION, chatMessage.getPosition());
        values.put(COLUMN_IS_ME, chatMessage.getIsMe());
        values.put(COLUMN_AVATAR, chatMessage.getUriAvatar());
        values.put(COLUMN_TYPE, chatMessage.getType());
        values.put(COLUMN_COLOR, chatMessage.getColor());

        long id = sqLiteDatabase.insert(TABLE_NOTE, null, values);
        closeDataBase();
        return true;
    }

    public boolean insertSticker(ChatMessage chatMessage) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POSITION, chatMessage.getPosition());
        values.put(COLUMN_AVATAR, chatMessage.getUriAvatar());
        values.put(COLUMN_TYPE, chatMessage.getType());
        values.put(COLUMN_COLOR, chatMessage.getColor());
        values.put(COLUMN_STICKER, chatMessage.getSticker());

        long id = sqLiteDatabase.insert(TABLE_NOTE, null, values);
        closeDataBase();
        return true;
    }

    public boolean updateChat(ChatMessage chatMessage, int id) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AVATAR, chatMessage.getUriAvatar());

        sqLiteDatabase.update(TABLE_NOTE, values, COLUMN_POSITION + "=" + id, null);
        closeDataBase();
        return true;
    }

    public boolean deleteItemFake(int id) {
        openDatabase();
        sqLiteDatabase.delete(TABLE_NOTE, "id=?", new String[]{String.valueOf(id)});
        closeDataBase();
        return true;
    }

    public void deleteAll(int id) {
        openDatabase();
        sqLiteDatabase.delete(TABLE_NOTE, COLUMN_POSITION + "=" + id, null);
        closeDataBase();
    }

    public void deleteAllChat() {
        openDatabase();
        sqLiteDatabase.delete(TABLE_NOTE, null, null);
        closeDataBase();
    }

    public ArrayList<ChatMessage> getMessage(int id) {
        ArrayList<ChatMessage> arrChat;
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *" +
                " FROM message WHERE " + COLUMN_POSITION + " = " + id, null);
        if (cursor == null) {
            closeDataBase();
            return new ArrayList<>();
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            closeDataBase();
            return new ArrayList<>();
        }
        arrChat = new ArrayList<>();
        cursor.moveToFirst();

        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexPosition = cursor.getColumnIndex(COLUMN_POSITION);
        int indexBody = cursor.getColumnIndex(COLUMN_BODY);
        int indexIsMe = cursor.getColumnIndex(COLUMN_IS_ME);
        int indexColor = cursor.getColumnIndex(COLUMN_COLOR);
        int indexType = cursor.getColumnIndex(COLUMN_TYPE);
        int indexAvatar = cursor.getColumnIndex(COLUMN_AVATAR);
        int indexSticker = cursor.getColumnIndex(COLUMN_STICKER);
        int indexLike = cursor.getColumnIndex(COLUMN_LIKE);

        while (!cursor.isAfterLast()) {
            int idd = cursor.getInt(indexId);
            int position = cursor.getInt(indexPosition);
            String body = cursor.getString(indexBody);
            String isMe = cursor.getString(indexIsMe);
            String avatar = cursor.getString(indexAvatar);
            int color = cursor.getInt(indexColor);
            int type = cursor.getInt(indexType);
            String sticker = cursor.getString(indexSticker);
            int like = cursor.getInt(indexLike);

            arrChat.add(new ChatMessage(idd, position, body, isMe, avatar, color, type, sticker, like));
            cursor.moveToNext();
        }
        closeDataBase();
        return arrChat;
    }

    public ChatMessage getLastPositionChat(int id) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *" +
                " FROM message where " + COLUMN_POSITION + " = " + id + " ORDER BY ID DESC LIMIT 1 ", null);
        if (cursor != null)
            cursor.moveToFirst();

        ChatMessage chatMessage = new ChatMessage(Integer.parseInt(cursor.getString(1))
                , cursor.getString(2)
                , cursor.getString(6)
                , cursor.getString(7));
        // return contact
        closeDataBase();
        return chatMessage;
    }
}
