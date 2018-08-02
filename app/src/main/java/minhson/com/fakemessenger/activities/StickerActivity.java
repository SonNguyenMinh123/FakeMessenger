package minhson.com.fakemessenger.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.adapter.VPStickerAdapter;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.fragment.CCCStickerFragment;
import minhson.com.fakemessenger.fragment.DocStickerFragment;
import minhson.com.fakemessenger.fragment.DoveStickerFragment;
import minhson.com.fakemessenger.fragment.MeepStickerFragment;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.utils.Constants;

/**
 * Created by Administrator on 8/8/2017.
 */

public class StickerActivity extends AppCompatActivity {
    private TabLayout tbSticker;
    private ViewPager vpSticker;
    private VPStickerAdapter viewPagerAdapter;
    private int id;
    private String uriPath;
    private SaveMessengerDataBaseMgr baseMgr;
    private int type;
    private boolean isMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        tbSticker = (TabLayout) findViewById(R.id.tbStick);
        vpSticker = (ViewPager) findViewById(R.id.vpSticker);
        viewPagerAdapter = new VPStickerAdapter(getSupportFragmentManager());

        addFragment();
        vpSticker.setAdapter(viewPagerAdapter);
        tbSticker.setupWithViewPager(vpSticker);
        viewPagerAdapter.notifyDataSetChanged();
        addIconTabLayout();
        //get Intent
        Intent intent = getIntent();
        id = intent.getIntExtra("idd", 0);
        uriPath = intent.getStringExtra("uri");
        isMe = intent.getBooleanExtra("isMe", false);
    }

    private void addFragment() {
        viewPagerAdapter.addFragment(new MeepStickerFragment());
        viewPagerAdapter.addFragment(new DoveStickerFragment());
        viewPagerAdapter.addFragment(new DocStickerFragment());
        viewPagerAdapter.addFragment(new CCCStickerFragment());
//        viewPagerAdapter.addFragment(new MeepStickerFragment());
    }

    private void addIconTabLayout() {
        ImageView iv1 = (ImageView) LayoutInflater.from(this).inflate(R.layout.customtab_sticker, null);
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.m_ic5);
        iv1.setImageBitmap(bm1);
        tbSticker.getTabAt(0).setCustomView(iv1);

        ImageView ivDove = (ImageView) LayoutInflater.from(this).inflate(R.layout.customtab_sticker, null);
        Bitmap bmDove = BitmapFactory.decodeResource(getResources(), R.drawable.dv_ic6);
        ivDove.setImageBitmap(bmDove);
        tbSticker.getTabAt(1).setCustomView(ivDove);

        ImageView iv2 = (ImageView) LayoutInflater.from(this).inflate(R.layout.customtab_sticker, null);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.dg_ic6);
        iv2.setImageBitmap(bm2);
        tbSticker.getTabAt(2).setCustomView(iv2);

        ImageView iv3 = (ImageView) LayoutInflater.from(this).inflate(R.layout.customtab_sticker, null);
        Bitmap bm3 = BitmapFactory.decodeResource(getResources(), R.drawable.ccc_ic10);
        iv3.setImageBitmap(bm3);
        tbSticker.getTabAt(3).setCustomView(iv3);
//
//        ImageView iv4 = (ImageView) LayoutInflater.from(this).inflate(R.layout.customtab_sticker, null);
//        Bitmap bm4 = BitmapFactory.decodeResource(getResources(), R.drawable.m_ic4);
//        iv4.setImageBitmap(bm4);
//        tbSticker.getTabAt(4).setCustomView(iv4);
    }

    public void clickItem(int pos, String name) {
        if (isMe) {
            type = 6;
        } else {
            type = 5;
        }

        ChatMessage chatMessage = new ChatMessage(id, "", uriPath, Color.parseColor("#1E88E5"), type, name);
        baseMgr = new SaveMessengerDataBaseMgr();
        baseMgr.insertSticker(chatMessage);
        Intent intent = new Intent(Constants.ADDSTICKER);
        intent.putExtra("name", name);
        sendBroadcast(intent);
        Log.e("insertSticker", "oke : " + name);
        finish();
    }
}
