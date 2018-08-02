package minhson.com.fakemessenger.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.item.ItemFakeChat;
import minhson.com.fakemessenger.utils.Constants;
import minhson.com.fakemessenger.utils.PreferenceUtils;

/**
 * Created by Administrator on 31/7/2017.
 */

public class SaveChangeChatActivity extends Activity implements View.OnClickListener {
    private static final int PICK_IMAGE = 100;
    private ImageView ivUser;
    private EditText edtEnterName;
    private TextView tvCreate;
    private RadioButton radioButton;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd");
    private DatabaseManager databaseManager = new DatabaseManager();
    private SaveMessengerDataBaseMgr baseMgr;
    private BroadcastReceiver receiverDeleteEnterName;
    private SharedPreferences sharedPre;
    private ItemFakeChat itemChange;
    private int id;
    private int pos;
    private String nameContact;
    private String uriImg;
    private String uriPath;
    private int typeUpdate;
    private ChatMessage chatMessage;
    private ArrayList<ItemFakeChat> listFake = new ArrayList<>();
    private ArrayList<ChatMessage> listChat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_chat);
        baseMgr = new SaveMessengerDataBaseMgr();
        baseMgr.copyDatabase(this);

        initViews();
    }

    private void initViews() {
        ivUser = (ImageView) findViewById(R.id.iv_user);
        edtEnterName = (EditText) findViewById(R.id.edt_enter_name);
        radioButton = (RadioButton) findViewById(R.id.rb_messenger);
        radioButton.setChecked(true);
        edtEnterName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        tvCreate = (TextView) findViewById(R.id.tv_create);
        sharedPre = PreferenceManager.getDefaultSharedPreferences(this);
        receiverDeleteEnterName = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                edtEnterName.setText("");
            }
        };
        registerReceiver(receiverDeleteEnterName, new IntentFilter(Constants.DELETE_ENTER_NAME));
        initEvent();
        //
        Intent intent = getIntent();
        nameContact = intent.getStringExtra("contact");
        id = intent.getIntExtra("id", 0);
        pos = intent.getIntExtra("pos", 0);
        uriPath = intent.getStringExtra("uriPath");
        typeUpdate = intent.getIntExtra("type", 0);
        edtEnterName.setText(nameContact);

        listFake = databaseManager.getAllItemFakeChat();
        listChat = baseMgr.getMessage(id);
        itemChange = listFake.get(pos);
        uriPath = itemChange.getAvatar();

        if (uriPath.equals(String.valueOf(R.drawable.icon_user_default))) {
            ivUser.setImageResource(R.drawable.icon_user_default);
        } else {
            Glide.with(this).load(Uri.parse(uriPath)).asBitmap().into(ivUser);
        }
        Log.e("urriiiii", uriPath);
    }

    private void initEvent() {
        ivUser.setOnClickListener(this);
        edtEnterName.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user:
                Intent getIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(getIntent, PICK_IMAGE);

                break;
            case R.id.edt_enter_name:

                break;
            case R.id.tv_create:
                clickTvSaveChangeFake();

                break;

            default:
                break;
        }
    }

    public void clickTvSaveChangeFake() {
        String avatar = "default";
        // chọn ảnh sẽ gán vào đường dẫn ảnh
        if (uriImg != null) {
            avatar = uriImg;
        } else if (uriPath != null) {
            avatar = uriPath;
        }

        String contact = edtEnterName.getText().toString();
        String messenger = "You are now connected on Messenger";
        String date = dateFormat.format(new Date());

        itemChange = new ItemFakeChat(id, avatar, contact, messenger, date);
        databaseManager.updateData(itemChange);
        if (listChat.size() != 0) {
            chatMessage = new ChatMessage(id, avatar);
            baseMgr.updateChat(chatMessage, id);
        }
        Log.e("uriiiiiiiiiiiiiiiii", " : " + avatar);

        Intent intent = new Intent(Constants.EDIT_NAME);
        intent.putExtra("nameContactt", contact);
        intent.putExtra("newUri", uriImg);
        sendBroadcast(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        //Lưu đường dẫn ảnh
                        uriImg = data.getData().toString();
                        InputStream imageStream = this.getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImagee = BitmapFactory.decodeStream(imageStream);
                        ivUser.setImageBitmap(selectedImagee);
                        PreferenceUtils.save("CLICK", true, this);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiverDeleteEnterName);
        super.onDestroy();
    }
}
