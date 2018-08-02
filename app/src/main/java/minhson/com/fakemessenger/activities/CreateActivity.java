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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.item.ItemFakeChat;
import minhson.com.fakemessenger.utils.Constants;
import minhson.com.fakemessenger.utils.PreferenceUtils;

/**
 * Created by Administrator on 25/7/2017.
 */

public class CreateActivity extends Activity implements View.OnClickListener {
    private static final int PICK_IMAGE = 100;
    private ImageView ivUser;
    private EditText edtEnterName;
    private TextView tvCreate;
    private RadioButton radioButton;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private DatabaseManager databaseManager = new DatabaseManager();
    private BroadcastReceiver receiverDeleteEnterName;
    private SharedPreferences sharedPre;
    private boolean is = false;
    private ItemFakeChat itemChange;
    private int i = 0;
    private int id;
    private String nameContact;
    private Bitmap selectedImagee;
    private String uriImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

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
                PreferenceUtils.save("CLICK", true, this);

                break;
            case R.id.edt_enter_name:

                break;
            case R.id.tv_create:
                String avatar = "default";
                int img = R.drawable.icon_user_default;
                // chọn ảnh sẽ gán vào đường dẫn ảnh
                if (uriImg != null){
                    avatar = uriImg;
                } else {
                    avatar = String.valueOf(img);
                }
                String contact = edtEnterName.getText().toString();
                String messenger = "You are now connected on Messenger";
                String date = dateFormat.format(new Date());

                itemChange = new ItemFakeChat(avatar, contact, messenger, date);
                is = databaseManager.insertNote(itemChange);
                Log.e("avatar = ", avatar);

                if (is) {
                    Toast.makeText(this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                this.sendBroadcast(new Intent("HIDE_TEXT"));
                Intent intent = new Intent(CreateActivity.this, ListItemActivity.class);
                startActivity(intent);
                PreferenceUtils.save(Constants.ADD_SUCEESS, true, this);
                finish();

                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    try {
                        uriImg = data.getData().toString();
                        InputStream imageStream = this.getContentResolver().openInputStream(Uri.parse(uriImg));
                        selectedImagee = BitmapFactory.decodeStream(imageStream);
                        ivUser.setImageBitmap(selectedImagee);
                        PreferenceUtils.save("GALLERY", true, CreateActivity.this);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiverDeleteEnterName);
        super.onDestroy();
    }
}
