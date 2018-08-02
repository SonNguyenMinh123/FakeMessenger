package minhson.com.fakemessenger.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.adapter.ListItemAdapter;
import minhson.com.fakemessenger.database.DatabaseManager;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.item.ItemFakeChat;
import minhson.com.fakemessenger.utils.Constants;
import minhson.com.fakemessenger.utils.PreferenceUtils;

/**
 * Created by Administrator on 22/7/2017.
 */

public class ListItemActivity extends Activity implements View.OnClickListener, ListItemAdapter.onClickListener {
    private static final int WRITE_EXTERNAL_STORAGE_1 = 1;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static int check = 0;
    private Button btCreate;
    private TextView tvAddText;
    private ImageView ivRate;
    private ImageView ivShare;
    private RecyclerView rv;
    private ArrayList<ItemFakeChat> litstItem = new ArrayList<>();
    private DatabaseManager data = new DatabaseManager();
    private SaveMessengerDataBaseMgr baseMgr = new SaveMessengerDataBaseMgr();
    private ArrayList<ChatMessage> listChat = new ArrayList<>();
    private BroadcastReceiver receivewHideText;
    private BroadcastReceiver receiverNotify;
    private BroadcastReceiver receiverEditName;
    private ListItemAdapter adapter;
    private ImageView ivTutorial;
    private int id;
    private String newUri;
    private String urll;
    private String GAME_PREFERENCES = "100";
    private Window window;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        DatabaseManager dataBaseManager = new DatabaseManager();
        dataBaseManager.copyDatabase(this);

        initUI();
        initViews();
        initEvent();
        receiver();
    }

    private void initViews() {
        litstItem = data.getAllItemFakeChat();
        adapter = new ListItemAdapter(this, litstItem);
        adapter.setClicked(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initEvent() {
        ivRate.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        btCreate.setOnClickListener(this);
        ivTutorial.setOnClickListener(this);
    }

    public void initUI(){
        sharedPreferences = getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        btCreate = (Button) findViewById(R.id.bt_create);
        rv = (RecyclerView) findViewById(R.id.rv_messenger);
        tvAddText = (TextView) findViewById(R.id.tv_text);
        ivTutorial = (ImageView) findViewById(R.id.iv_tutorial);
        ivRate = (ImageView) findViewById(R.id.iv_ic_rate);
        ivShare = (ImageView) findViewById(R.id.iv_ic_like);
        window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0D47A1"));

        if (PreferenceUtils.getBooleanFromPreference(Constants.ADD_SUCEESS, this)) {
            tvAddText.setVisibility(View.GONE);
        } else if (PreferenceUtils.getBooleanFromPreference("save_addtext", ListItemActivity.this)) {
            tvAddText.setVisibility(View.VISIBLE);
        }

    }

    public void receiver(){
        //receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.HIDE_TEXT);
        receivewHideText = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Constants.HIDE_TEXT:
                        tvAddText.setVisibility(View.GONE);

                        break;
                }
            }
        };
        registerReceiver(receivewHideText, filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_create:
                Intent intent = new Intent(ListItemActivity.this, CreateActivity.class);
                startActivity(intent);
                sendBroadcast(new Intent(Constants.DELETE_ENTER_NAME));

                break;

            case R.id.iv_tutorial:
                Intent intentTutorial = new Intent(ListItemActivity.this, TutorialActivity.class);
                startActivity(intentTutorial);

                break;

            case R.id.iv_ic_rate:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

                break;
            case R.id.iv_ic_like:
                final String appPackageName = getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Aap Ka Sewak App at: https://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiverNotitifyChange();
    }

    public void receiverNotitifyChange() {
        //receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.NOTIFY);
        filter.addAction("notifyy");
        filter.addAction("set_up_adapter");
        filter.addAction(Constants.SETUP_ADAPTER_LAST_POS);

        receiverNotify = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Constants.NOTIFY:
                        initViews();
                        if (adapter.getItemCount() == 0) {
                            tvAddText.setVisibility(View.VISIBLE);
                            PreferenceUtils.save("save_addtext", true, ListItemActivity.this);
                            Log.e("notify", "oke");
                        }

                        break;

                    case "notifyy":
                        initViews();

                        break;

                    case "set_up_adapter":
                        initViews();
                        if (adapter.getItemCount() == 0) {
                            tvAddText.setVisibility(View.VISIBLE);
                            PreferenceUtils.save("save_addtext", true, ListItemActivity.this);
                        }

                        break;

                    case Constants.SETUP_ADAPTER_LAST_POS:
                        initViews();

                        break;

                    default:
                        break;
                }
            }
        };
        registerReceiver(receiverNotify, filter);
        //
        receiverEditName = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initViews();
                adapter.setListItem(litstItem);
            }
        };
        registerReceiver(receiverEditName, new IntentFilter(Constants.EDIT_NAME));
    }

    @Override
    public void selected(int pos, String url) {
        this.id = pos;
        this.urll = url;
        Intent intent = new Intent(ListItemActivity.this, MessengerActivity.class);
        intent.putExtra("id", litstItem.get(pos).getId());
        intent.putExtra("pos", pos);
        intent.putExtra("url", url);
        intent.putExtra("namecontact", litstItem.get(pos).getContact());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        initViews();
        adapter.setListItem(litstItem);
        finish();
        sendBroadcast(new Intent(Constants.FINISH_ACT_MAIN));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiverEditName);
        unregisterReceiver(receiverNotify);
        unregisterReceiver(receivewHideText);
        super.onDestroy();
    }

}
