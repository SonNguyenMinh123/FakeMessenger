package minhson.com.fakemessenger.activities;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import minhson.com.fakemessenger.R;

/**
 * Created by Administrator on 10/8/2017.
 */

public class ResultActivity extends Activity {
    private ImageView ivResult;
    private LinearLayout lnSetWallPaper;
    private LinearLayout lnShare;
    private String path;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ss);

        initViews();
    }

    private void initViews() {
        ivResult = (ImageView) findViewById(R.id.image_result);
        lnSetWallPaper = (LinearLayout) findViewById(R.id.set_as_wallpaper);
        lnShare = (LinearLayout) findViewById(R.id.share);
        ivBack = (ImageView) findViewById(R.id.im_back_result);
        //get Intent
        Intent intent = getIntent();
        switch (intent.getAction()) {
            case "show.result":
                try {
                    path = getIntent().getStringExtra("path");
                    Glide.with(ResultActivity.this).load(new File(path)).asBitmap().into(ivResult);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        lnSetWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaperDevice(getBitmap(path));
            }
        });

        lnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(Uri.fromFile(new File(path)));
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setWallpaperDevice(Bitmap bitmap) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // get the height and width of screen
        final int height = metrics.heightPixels;
        final int width = metrics.widthPixels;

        final Bitmap bitmap_after = Bitmap.createScaledBitmap(bitmap, width, height, true);
        try {
            WallpaperManager myWall = WallpaperManager.getInstance(ResultActivity.this);
            myWall.setBitmap(bitmap_after);
            Toast.makeText(ResultActivity.this, R.string.wallpaper_success, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ResultActivity.this, R.string.wallpaper_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmap(String path) {
        Uri uri = Uri.fromFile(new File(path));
        InputStream in;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap b;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x, (int) y, true);
                b.recycle();
                b = scaledBitmap;
                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();
            return b;
        } catch (IOException e) {
            return null;
        }
    }

    private void shareImage(Uri bmpUri) {
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(this, R.string.sharing_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
