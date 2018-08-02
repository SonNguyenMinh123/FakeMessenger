package minhson.com.fakemessenger.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.item.ObjectTutorial;

/**
 * Created by Administrator on 14/8/2017.
 */

public class TutorialPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ObjectTutorial> listTutorial;
    private View view;
    private ImageView ivItem;
    private TextView tvFuncion;

    public TutorialPagerAdapter(Context context) {
        this.context = context;
        initData();
    }

    private void initData() {
        listTutorial = new ArrayList<>();
        listTutorial.add(new ObjectTutorial(R.drawable.bg_cam, context.getResources().getString(R.string.camera)));
        listTutorial.add(new ObjectTutorial(R.drawable.bg_picture, context.getResources().getString(R.string.gallery)));
        listTutorial.add(new ObjectTutorial(R.drawable.bg_name, context.getResources().getString(R.string.contact)));
        listTutorial.add(new ObjectTutorial(R.drawable.bg_dotsss, context.getResources().getString(R.string.change_color)));
        listTutorial.add(new ObjectTutorial(R.drawable.bg_sticker, context.getResources().getString(R.string.sticker)));
    }

    @Override
    public int getCount() {
        return listTutorial.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup viewPager, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_tutorial, viewPager, false);
        ivItem = (ImageView) view.findViewById(R.id.iv_item_tutorial);
        tvFuncion = (TextView) view.findViewById(R.id.tv_function);

        ivItem.setImageResource(listTutorial.get(position).getImage());
        tvFuncion.setText(listTutorial.get(position).getFunction());

        viewPager.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup viewPager, int position, Object view) {
        viewPager.removeView((View) view);
    }
}
