package minhson.com.fakemessenger.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.activities.StickerActivity;
import minhson.com.fakemessenger.item.EventSticker;

/**
 * Created by dee on 16/02/2017.
 */

public class RVStickerAdapter extends RecyclerView.Adapter<RVStickerAdapter.ViewHolder> {
    private static final String TAG = "RCSickerAdapter";
    private Context mContext;
    private IClickItemRycyclerVieww iClickItemRycyclerView;
    private EventBus mEventBus;
    private List<String> listSticker;
    private StickerActivity stickerFragment;

    public RVStickerAdapter(Context context, List<String> listStickerr) {
        mContext = context;
        mEventBus = EventBus.getDefault();
        listSticker = listStickerr;
        stickerFragment = (StickerActivity) context;
    }

    private void addSticker() {
//        listSticker = new ArrayList<>();
//        listSticker.add(new ObjSticker(R.raw.ccc1));
//        listSticker.add(new ObjSticker(R.raw.ccc2));
//        listSticker.add(new ObjSticker(R.raw.ccc3));
//        listSticker.add(new ObjSticker(R.raw.ccc4));
//        listSticker.add(new ObjSticker(R.raw.ccc5));
//        listSticker.add(new ObjSticker(R.raw.ccc6));
//
//        listSticker.add(new ObjSticker(R.raw.dg1));
//        listSticker.add(new ObjSticker(R.raw.dg2));
//        listSticker.add(new ObjSticker(R.raw.dg3));
//        listSticker.add(new ObjSticker(R.raw.dg4));
//        listSticker.add(new ObjSticker(R.raw.dg5));
//        listSticker.add(new ObjSticker(R.raw.dg6));
//        listSticker.add(new ObjSticker(R.raw.dg7));
//        listSticker.add(new ObjSticker(R.raw.dg8));
//
//        listSticker.add(new ObjSticker(R.raw.dv1));
//        listSticker.add(new ObjSticker(R.raw.dv2));
//        listSticker.add(new ObjSticker(R.raw.dv3));
//        listSticker.add(new ObjSticker(R.raw.dv4));
//        listSticker.add(new ObjSticker(R.raw.dv5));
//        listSticker.add(new ObjSticker(R.raw.dv6));
//        listSticker.add(new ObjSticker(R.raw.dv7));
//        listSticker.add(new ObjSticker(R.raw.dv8));
//
//        listSticker.add(new ObjSticker(R.raw.me1));
//        listSticker.add(new ObjSticker(R.raw.me2));
//        listSticker.add(new ObjSticker(R.raw.me3));
//        listSticker.add(new ObjSticker(R.raw.me4));
//        listSticker.add(new ObjSticker(R.raw.me5));
//        listSticker.add(new ObjSticker(R.raw.me6));
//        listSticker.add(new ObjSticker(R.raw.me7));
//        listSticker.add(new ObjSticker(R.raw.me8));
//        listSticker.add(new ObjSticker(R.raw.me9));
//        listSticker.add(new ObjSticker(R.raw.me10));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String name = listSticker.get(position);
        final EventSticker eventSticker = new EventSticker(name);
        try {
            InputStream is = mContext.getAssets().open(name);
            Drawable d =  Drawable.createFromStream(is,null);
            holder.ivStckerView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventBus.postSticky(eventSticker);
                stickerFragment.clickItem(holder.getAdapterPosition(), name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSticker.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivStckerView;
        public View vContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
            ivStckerView = (ImageView) itemView.findViewById(R.id.ivItemSticker);
        }
    }

    public interface IClickItemRycyclerVieww {
        void onItemClick(int pos);
    }
}
