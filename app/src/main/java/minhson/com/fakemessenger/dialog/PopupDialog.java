package minhson.com.fakemessenger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.activities.SaveChangeChatActivity;
import minhson.com.fakemessenger.activities.TutorialActivity;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.utils.Constants;
import minhson.com.fakemessenger.utils.PreferenceUtils;

/**
 * Created by Administrator on 14/8/2017.
 */

public class PopupDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tvEditContact;
    private TextView tvDeleteContact;
    private TextView tvChatColor;
    private TextView tvClearChat;
    private TextView tvTutorial;

    private int id;
    private String nameContact;
    private String urlPath;
    private int type;
    private int pos;

    private ArrayList<ChatMessage> listChat = new ArrayList<>();
    private SaveMessengerDataBaseMgr saveMessageDataBase = new SaveMessengerDataBaseMgr();

    public PopupDialog(@NonNull Context context, int id,
                       String nameContact, String urlPath, int type, int position) {
        super(context);
        this.context = context;
        this.id = id;
        this.nameContact = nameContact;
        this.urlPath = urlPath;
        this.type = type;
        this.pos = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_popup);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initDialog();
        listChat = saveMessageDataBase.getMessage(id);
    }

    private void initDialog() {
        tvEditContact = (TextView) findViewById(R.id.tv_edit_contact);
        tvDeleteContact = (TextView) findViewById(R.id.tv_delete_contact);
//        tvChatColor = (TextView) findViewById(R.id.tv_color);
        tvClearChat = (TextView) findViewById(R.id.tv_clear_chat);
        tvTutorial = (TextView) findViewById(R.id.tv_tutorial);

        initEvent();
    }

    private void initEvent() {
        tvEditContact.setOnClickListener(this);
        tvDeleteContact.setOnClickListener(this);
//        tvChatColor.setOnClickListener(this);
        tvClearChat.setOnClickListener(this);
        tvTutorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_contact:
                final Intent intent = new Intent(context, SaveChangeChatActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("contact", nameContact);
                intent.putExtra("uriPath", urlPath);
                intent.putExtra("type", type);
                intent.putExtra("pos", pos);
                context.startActivity(intent);
                PreferenceUtils.save(Constants.SAVE_UPDATE, true, context);
                Log.e("zzzzzzzzz", "oke" + pos);
                dismiss();

                break;
            case R.id.tv_delete_contact:
                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.deleteData(id);
                context.sendBroadcast(new Intent(Constants.NOTIFY));
                saveMessageDataBase.deleteAll(id);
                dismiss();

                break;
//            case R.id.tv_color:
//                ChangColorDialog colorDialog = new ChangColorDialog(context, new ChangColorDialog.getColorPicker() {
//                    @Override
//                    public void btnChon(int color) {
//                        Intent intentColor = new Intent("COLOR");
//                        intentColor.putExtra("color", color);
//                        context.sendBroadcast(intentColor);
//                        PreferenceUtils.save("CONTEXT_MENU_COLOR", color, context);
//                        Log.e("insertcolor", "" + id + "," + color);
//                    }
//                });
//                colorDialog.show();
//                dismiss();
//
//                break;
            case R.id.tv_clear_chat:
                saveMessageDataBase.deleteAll(id);
                context.sendBroadcast(new Intent("setUpAdapter"));
                dismiss();

                break;

            case R.id.tv_tutorial:
                Intent intentTutorial = new Intent(context, TutorialActivity.class);
                context.startActivity(intentTutorial);
                dismiss();

                break;
            default:
                break;

        }
    }
}
