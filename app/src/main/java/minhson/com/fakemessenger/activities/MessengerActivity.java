package minhson.com.fakemessenger.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.adapter.ConversionAdapter;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.dialog.PopupDialog;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.item.ItemFakeChat;
import minhson.com.fakemessenger.utils.Constants;
import minhson.com.fakemessenger.utils.PreferenceUtils;

import static minhson.com.fakemessenger.activities.ListItemActivity.check;
import static minhson.com.fakemessenger.activities.ListItemActivity.editor;

/**
 * Created by Administrator on 22/7/2017.
 */

public class MessengerActivity extends Activity implements View.OnClickListener, ConversionAdapter.DeleteItem {
    private static final int PICK_IMAGE = 1000;
    public static int index = -1;
    public static int top = -1;
    private ImageView ivBack;
    private ImageView ivDown;
    private ImageView ivSendMessage;
    private ImageView ivDots;
    private ImageView ivSmile;
    private ImageView ivLikes;
    private ImageView ivSmileMessenger;
    private ImageView ivPicture;
    private ImageView ivSelected;
    private ImageView ivIconLike;
    private ImageView ivScreenShots;
    private ImageView ivCameraScreenShots;
    private LinearLayout lnMicro;
    private LinearLayout lnSendMessage;
    private LinearLayout lnAa;
    private LinearLayout lnback;
    private LinearLayout lnAddADay;
    private RecyclerView recycle;
    private RelativeLayout rlActionBar;
    private LinearLayout lnMessenger;
    private ImageView ivPlus;
    private ConversionAdapter chatAdapter;
    private ArrayList<ChatMessage> listChat = new ArrayList<>();
    private EditText edtMessage;
    private TextView tvNameContact;
    private TextView tvChangeContact;
    private LinearLayout lnNameContact;
    private String nameContact = "";
    private SaveMessengerDataBaseMgr saveMessageDataBase;
    private DatabaseManager databaseManager = new DatabaseManager();
    private int id;
    private BroadcastReceiver receiver;
    private BroadcastReceiver receiverColor;
    private BroadcastReceiver receiverDeleteAll;
    private BroadcastReceiver receiverUpdate;
    private BroadcastReceiver receiversetUpAdapter;
    private int pos;
    private String nameContactt;
    private String uriImg;
    private String urlPath;
    private boolean isMe = false;
    private ChatMessage chatMessage;
    private String uriAvatar;
    private String newUriUpdate;
    private int type;
    private int changeColor;
    private int color;
    private MediaPlayer mediaPlayer;
    private PopupDialog popupDialog;
    private LinearLayoutManager manager;
    private Window window;

    private void takeScreenshot() throws IOException {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String fileName = now + ".jpg";
        try {
            String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "";
            File folder = new File(folderPath);
            folder.mkdirs();  //create directory

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(folder, fileName);
            imageFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(MessengerActivity.this, "ScreenShot Captured", Toast.LENGTH_SHORT).show();
            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
            Intent intentResult = new Intent(MessengerActivity.this, ResultActivity.class);
            intentResult.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentResult.setAction("show.result");
            intentResult.putExtra("path", folderPath + "/" + fileName);
            startActivity(intentResult);
            finish();
            Log.e("SCREEN_SHOT", "oke");
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        saveMessageDataBase = new SaveMessengerDataBaseMgr();
        saveMessageDataBase.copyDatabase(this);

        initFindviewById();
        initGetIntent();
        initViews();
        initEvent();
        changeColor();

    }

    private void initGetIntent() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        pos = intent.getIntExtra("pos", 0);
        urlPath = intent.getStringExtra("url");
        nameContact = intent.getStringExtra("namecontact");
        tvNameContact.setText(nameContact);
        tvChangeContact.setText(nameContact);

        listChat = saveMessageDataBase.getMessage(id);
        ItemFakeChat itemFakeChat = new ItemFakeChat(id, urlPath);
        itemFakeChat = databaseManager.getFakeChat(id);
        urlPath = itemFakeChat.getAvatar();

    }

    public void initFindviewById() {
        ivBack = (ImageView) findViewById(R.id.iv_back_messenger_main);
        ivDown = (ImageView) findViewById(R.id.iv_down);
        ivSendMessage = (ImageView) findViewById(R.id.iv_send);
        ivDots = (ImageView) findViewById(R.id.iv_dots);
        ivSmile = (ImageView) findViewById(R.id.iv_smile);
        ivLikes = (ImageView) findViewById(R.id.iv_like_mesenger);
        ivIconLike = (ImageView) findViewById(R.id.iv_likeeee);
        ivPicture = (ImageView) findViewById(R.id.iv_pictu);
        ivPicture.setColorFilter(Color.parseColor("#1E88E5"));
        ivSelected = (ImageView) findViewById(R.id.iv_selected);
        ivCameraScreenShots = (ImageView) findViewById(R.id.iv_camera);
        ivScreenShots = (ImageView) findViewById(R.id.iv_screen_shots);
        ivSmileMessenger = (ImageView) findViewById(R.id.iv_smile_messenger);
        tvNameContact = (TextView) findViewById(R.id.tv_name_contact);
        lnMicro = (LinearLayout) findViewById(R.id.ln_micro);
        lnSendMessage = (LinearLayout) findViewById(R.id.ln_send_message);
        lnMessenger = (LinearLayout) findViewById(R.id.ln_messenger);
        lnAa = (LinearLayout) findViewById(R.id.ln_aa);
        lnback = (LinearLayout) findViewById(R.id.lnnnBack);
        lnAddADay = (LinearLayout) findViewById(R.id.ln_add_a_day);
        recycle = (RecyclerView) findViewById(R.id.lv_message);
        edtMessage = (EditText) findViewById(R.id.edt_write);
        tvChangeContact = (TextView) findViewById(R.id.tv_name_change_contact);
        lnNameContact = (LinearLayout) findViewById(R.id.ln_name_contact);
        rlActionBar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        ivPlus = (ImageView) findViewById(R.id.iv_plus);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0D47A1"));
    }

    private void initEvent() {
        ivDown.setOnClickListener(this);
        ivSendMessage.setOnClickListener(this);
        lnMicro.setOnClickListener(this);
        lnAa.setOnClickListener(this);
        lnback.setOnClickListener(this);
        tvChangeContact.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        ivSmileMessenger.setOnClickListener(this);
        ivIconLike.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        ivCameraScreenShots.setOnClickListener(this);
        ivDots.setOnClickListener(this);
    }

    private void initViews() {
        listChat = saveMessageDataBase.getMessage(id);
        chatAdapter = new ConversionAdapter(listChat, MessengerActivity.this, urlPath, MessengerActivity.this, id);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    private void changeColor() {
        //change color
//        changeColor = PreferenceUtils.getIntFromPreference("CONTEXT_MENU_COLOR", MessengerActivity.this);
//        if (changeColor != 0) {
//            rlActionBar.setBackgroundColor(changeColor);
//            ivPlus.setColorFilter(changeColor);
//            GradientDrawable gd = new GradientDrawable();
//            gd.setColor(changeColor);
//            gd.setCornerRadius(48f);
//            lnNameContact.setBackgroundDrawable(gd);
//            ivSendMessage.setColorFilter(changeColor);
//            ivDown.setColorFilter(changeColor);
//            ivLikes.setColorFilter(changeColor);
//            ivSmile.setColorFilter(changeColor);
//            ivSmileMessenger.setColorFilter(changeColor);
//            ivPicture.setColorFilter(changeColor);
//            ivIconLike.setColorFilter(changeColor);
//            window.setStatusBarColor(changeColor);
//        }
        //
        if (PreferenceUtils.getBooleanFromPreference("isMe", this)) {
            tvChangeContact.setText("You");
            isMe = true;
        } else {
            tvChangeContact.setText(nameContact);
            isMe = false;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnnnBack:
                onBackPressed();

                break;

            case R.id.iv_dots:
                popupDialog = new PopupDialog(this, id, nameContact, urlPath, type, pos);
                popupDialog.show();

                break;

            case R.id.iv_plus:
                lnMicro.setVisibility(View.GONE);
                lnAddADay.setVisibility(View.GONE);
                lnSendMessage.setVisibility(View.VISIBLE);
                edtMessage.requestFocus();
                InputMethodManager immShow = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                immShow.showSoftInput(edtMessage, InputMethodManager.SHOW_IMPLICIT);

                break;

            case R.id.ln_aa:
                lnMicro.setVisibility(View.GONE);
                lnAddADay.setVisibility(View.GONE);
                lnSendMessage.setVisibility(View.VISIBLE);
                edtMessage.requestFocus();
                InputMethodManager immShowLn = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                immShowLn.showSoftInput(edtMessage, InputMethodManager.SHOW_IMPLICIT);

                break;

            case R.id.iv_down:
                lnMicro.setVisibility(View.VISIBLE);
                lnAddADay.setVisibility(View.VISIBLE);
                lnSendMessage.setVisibility(View.GONE);
                ivDots.setImageResource(R.drawable.icon_than);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                edtMessage.requestFocus();

                break;

            case R.id.tv_name_change_contact:
                check = check + 1;
                int nextIndex = listChat.size();
                editor.putInt("nextIndex", nextIndex);
                editor.commit();
                Log.e("check", "" + check);
                if (!isMe) {
                    tvChangeContact.setText("You");
                    PreferenceUtils.save("isMe", true, MessengerActivity.this);

                } else {
                    tvChangeContact.setText(nameContact);
                    PreferenceUtils.save("isMe", false, MessengerActivity.this);

                }
                isMe = !isMe;

                break;

            case R.id.iv_camera:
                try {
                    takeScreenshot();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.iv_send:
                if (edtMessage.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.enter_character), Toast.LENGTH_SHORT).show();
                } else {
                    sendTextMessage(view);
                }

                break;

            case R.id.iv_pictu:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE);

                break;

            case R.id.iv_smile_messenger:
                Intent intentSticker = new Intent(MessengerActivity.this, StickerActivity.class);
                intentSticker.putExtra("idd", id);
                intentSticker.putExtra("uri", urlPath);
                intentSticker.putExtra("isMe", isMe);
                startActivity(intentSticker);

                break;

            case R.id.iv_likeeee:
                if (isMe) {
                    type = 8;
                } else {
                    type = 7;
                }

                ChatMessage cme = new ChatMessage(id, "", urlPath, Color.parseColor("#1E88E5"), type);
                saveMessageDataBase.insertImage(cme);
                listChat.add(cme);
                chatAdapter.setListChat(listChat);
                recycle.smoothScrollToPosition(listChat.size());
                chatAdapter.notifyDataSetChanged();

                break;

            default:
                break;
        }
    }

    public void sendTextMessage(View v) {
        uriAvatar = "default";
        if (urlPath != null) {
            uriAvatar = urlPath;
        } else {

        }

        String message = edtMessage.getText().toString();
        if (isMe) {
            type = 2;
        } else {
            type = 1;
        }
        chatMessage = new ChatMessage(id, message, "", urlPath, Color.parseColor("#1E88E5"), type);
        saveMessageDataBase.insertChat(chatMessage);
        listChat.add(chatMessage);
        int pos = listChat.size();
        editor.putInt("lastitem", pos);
        editor.commit();
        edtMessage.setText("");
        recycle.smoothScrollToPosition(listChat.size());
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        //
        index = manager.findFirstVisibleItemPosition();
        View v = recycle.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - recycle.getPaddingTop());
    }

    @Override
    public void onResume() {
        super.onResume();
        // set recyclerview position
        if (index != -1) {
            manager.scrollToPositionWithOffset(index, top);
        }
    }

    public void receiver() {
        receiverUpdate = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                newUriUpdate = bundle.getString("newUri");
                nameContactt = bundle.getString("nameContactt");

                tvNameContact.setText(nameContactt);
                tvChangeContact.setText(nameContactt);
                if (nameContact != null) {
                    nameContact = nameContactt;
                }
                if (urlPath != null) {
                    urlPath = newUriUpdate;
                }
                initViews();
                if (PreferenceUtils.getBooleanFromPreference("isMe", MessengerActivity.this)) {
                    tvChangeContact.setText("You");
                    isMe = true;
                } else {
                    tvChangeContact.setText(nameContact);
                    isMe = false;
                }
            }
        };
        registerReceiver(receiverUpdate, new IntentFilter(Constants.EDIT_NAME));
        //receiver------------------------------------------
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ADDSTICKER);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Constants.ADDSTICKER:
                        initViews();
                        recycle.smoothScrollToPosition(listChat.size());

                        break;

                    default:
                        break;
                }
            }
        };
        registerReceiver(receiver, filter);
        //
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.NOTIFY);
        intentFilter.addAction("setUpAdapter");
        intentFilter.addAction("set_up_adapter");
        receiverDeleteAll = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Constants.NOTIFY:
                        initViews();
                        finish();
                        break;

                    case "setUpAdapter":
                        initViews();
                        Log.e("AAAAA", "oke");

                        break;

                    case "set_up_adapter":
                        initViews();

                        break;
                    default:
                        break;
                }
            }
        };
        registerReceiver(receiverDeleteAll, intentFilter);
        //
//        receiverColor = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getExtras();
//                color = bundle.getInt("color");
//                rlActionBar.setBackgroundColor(color);
//                GradientDrawable gd = new GradientDrawable();
//                gd.setColor(color);
//                gd.setCornerRadius(48f);
//                lnNameContact.setBackgroundDrawable(gd);
//                ivPlus.setColorFilter(color);
//                ivSendMessage.setColorFilter(color);
//                ivDown.setColorFilter(color);
//                ivLikes.setColorFilter(color);
//                ivSmile.setColorFilter(color);
//                ivSmileMessenger.setColorFilter(color);
//                ivPicture.setColorFilter(color);
//                ivIconLike.setColorFilter(color);
//                window.setStatusBarColor(color);
//
//            }
//        };
//        registerReceiver(receiverColor, new IntentFilter("COLOR"));
        //
//        receiversetUpAdapter = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getExtras();
//                String lastPos = bundle.getString("lasPos");
//                if (type == 2) {
//                    chatMessage = new ChatMessage(id, lastPos, "", urlPath, Color.parseColor("#1E88E5"), type);
//                    saveMessageDataBase.insertChat(chatMessage);
//                    listChat.add(chatMessage);
//                    recycle.smoothScrollToPosition(listChat.size());
//                    chatAdapter.notifyDataSetChanged();
//                }
//            }
//        };
//        registerReceiver(receiversetUpAdapter, new IntentFilter(Constants.SETUP_ADAPTER));
        // save text final
        Intent intent = new Intent("TEXT_FINAL");
        intent.putExtra("text_final", listChat.size());
        sendBroadcast(intent);
        Log.e("pos_final", "" + listChat.size());
    }

    @Override
    protected void onUserLeaveHint() {
        Log.e("home", "oke");
        super.onUserLeaveHint();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    uriAvatar = "default";
                    uriImg = data.getData().toString();

                    if (isMe) {
                        type = 4;
                    } else {
                        type = 3;
                    }

                    ChatMessage cme = new ChatMessage(id, uriImg, urlPath, Color.parseColor("#1E88E5"), type);
                    saveMessageDataBase.insertImage(cme);
                    listChat.add(cme);
                    chatAdapter.setListChat(listChat);
                    recycle.smoothScrollToPosition(listChat.size());
                    chatAdapter.notifyDataSetChanged();
                    Log.e("listChat", " " + listChat);
                }

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void deleted(int position) {
        chatAdapter.setDeleteItem(this);
        saveMessageDataBase.deleteItemFake(position);
        initViews();
        recycle.smoothScrollToPosition(listChat.size() - 1);
        Log.e("delete", "oke");
    }

    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent(Constants.SETUP_ADAPTER_LAST_POS));
        finish();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiverUpdate);
        unregisterReceiver(receiverDeleteAll);
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
