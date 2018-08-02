package minhson.com.fakemessenger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.activities.SaveChangeChatActivity;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.item.ChatMessage;

/**
 * Created by Administrator on 11/8/2017.
 */

public class DeleteContactDialog extends Dialog implements View.OnClickListener {
    private Button btnEditName;
    private Button btnDelete;
    private int id;
    private String nameContact;
    private String uri;
    private int position;
    private DatabaseManager data = new DatabaseManager();
    private SaveMessengerDataBaseMgr baseMgr = new SaveMessengerDataBaseMgr();
    private ArrayList<ChatMessage> listChat = new ArrayList<>();

    public DeleteContactDialog(@NonNull Context context, int id, String nameContact, String uri, int pos) {
        super(context);
        this.id = id;
        this.nameContact = nameContact;
        this.uri = uri;
        this.position = pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_contact);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        btnEditName = (Button) findViewById(R.id.bt_editname);
        btnDelete = (Button) findViewById(R.id.bt_delete);

        btnEditName.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_editname:
                Intent intent = new Intent(getContext(), SaveChangeChatActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("contact", nameContact);
                intent.putExtra("uriPath", uri);
                intent.putExtra("pos", position);
                getContext().startActivity(intent);
                dismiss();

                break;

            case R.id.bt_delete:
                data.deleteData(id);
                baseMgr.deleteAll(id);
                getContext().sendBroadcast(new Intent("set_up_adapter"));
                dismiss();

                break;
            default:
                break;
        }
    }
}
