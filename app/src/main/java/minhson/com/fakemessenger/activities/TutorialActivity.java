package minhson.com.fakemessenger.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.adapter.TutorialPagerAdapter;
import minhson.com.fakemessenger.item.ObjectTutorial;

/**
 * Created by Administrator on 14/8/2017.
 */

public class TutorialActivity extends Activity implements View.OnClickListener {
    private LinearLayout lnBackTutorial;
    private ViewPager viewPager;
    private ArrayList<ObjectTutorial> listTutorial;
    private TutorialPagerAdapter tutorialPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
    }

    private void initViews() {
        lnBackTutorial = (LinearLayout) findViewById(R.id.ln_back_tutorial);
        viewPager = (ViewPager) findViewById(R.id.vp_tutorial);
        listTutorial = new ArrayList<>();
        tutorialPagerAdapter = new TutorialPagerAdapter(this);
        viewPager.setAdapter(tutorialPagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator_tutorial);
        indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(1);

        initEvent();
    }

    private void initEvent() {
        lnBackTutorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ln_back_tutorial:
                onBackPressed();

                break;
            default:
                break;
        }
    }
}
