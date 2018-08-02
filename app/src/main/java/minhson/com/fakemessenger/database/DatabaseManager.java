package minhson.com.fakemessenger.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import minhson.com.fakemessenger.item.ItemFakeChat;

/**
 * Created by Administrator on 22/7/2017.
 */

public class DatabaseManager {
    private static final String DATABASE_PATH = Environment.getDataDirectory().getAbsolutePath()
            + "/data/minhson.com.fakemessenger/";
    private static final String DATABASE_NAME = "fakechat.sqlite";
    private static final String TABLE_NOTE = "tblfakechat";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AVATAR = "avatart";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_MESSENGER = "messenger";
    private static final String COLUMN_DATE = "date";
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

    public boolean insertNote(ItemFakeChat itemFakeChat) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AVATAR, itemFakeChat.getAvatar());
        values.put(COLUMN_CONTACT, itemFakeChat.getContact());
        values.put(COLUMN_MESSENGER, itemFakeChat.getMessenger());
        values.put(COLUMN_DATE, itemFakeChat.getDate());
        Log.d("Avatar" , itemFakeChat.getAvatar());

        long id = sqLiteDatabase.insert(TABLE_NOTE, null, values);
        closeDataBase();

        return true;
    }

    public boolean updateData(ItemFakeChat itemFakeChat) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AVATAR, itemFakeChat.getAvatar());
        values.put(COLUMN_CONTACT, itemFakeChat.getContact());
        values.put(COLUMN_MESSENGER, itemFakeChat.getMessenger());
        sqLiteDatabase.update(TABLE_NOTE, values, "id=?", new String[]{String.valueOf(itemFakeChat.getId())});
        closeDataBase();
        return true;
    }

    public boolean deleteData(int id) {
        openDatabase();
        sqLiteDatabase.delete(TABLE_NOTE, "id=?", new String[]{String.valueOf(id)});
        closeDataBase();
        return true;
    }

    public ItemFakeChat getFakeChat(int id) {
        openDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NOTE, new String[]{COLUMN_ID,
                        COLUMN_AVATAR}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null )
            cursor.moveToFirst();

        ItemFakeChat itemFakeChat = new ItemFakeChat(Integer.parseInt(cursor.getString(0))
                ,cursor.getString(1));
        // return contact
        closeDataBase();
        return itemFakeChat;

    }

    public ArrayList<ItemFakeChat> getAllItemFakeChat() {
        ArrayList<ItemFakeChat> arr;
        String sql = "select id,avatart,contact,messenger,date from tblfakechat";
        openDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor == null) {
            closeDataBase();
            return new ArrayList<>();
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            closeDataBase();
            return new ArrayList<>();
        }
        arr = new ArrayList<>();
        cursor.moveToFirst();
        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexAvatar = cursor.getColumnIndex(COLUMN_AVATAR);
        int indexContact = cursor.getColumnIndex(COLUMN_CONTACT);
        int indexMessenger = cursor.getColumnIndex(COLUMN_MESSENGER);
        int indexDate = cursor.getColumnIndex(COLUMN_DATE);

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(indexId);
            String avatar = cursor.getString(indexAvatar);
            String contact = cursor.getString(indexContact);
            String messenger = cursor.getString(indexMessenger);
            String date = cursor.getString(indexDate);
            Log.d("eeasadsad",avatar);
            arr.add(new ItemFakeChat(id, avatar, contact, messenger, date));
            cursor.moveToNext();
        }

        closeDataBase();
        return arr;
    }
}
