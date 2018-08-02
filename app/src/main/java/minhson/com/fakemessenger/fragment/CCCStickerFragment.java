package minhson.com.fakemessenger.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.adapter.RVStickerAdapter;
import minhson.com.fakemessenger.item.ManagerSticker;

/**
 * Created by dee on 17/02/2017.
 */

public class CCCStickerFragment extends BaseFragment {
    private RecyclerView rvStickerMeep;
    private RVStickerAdapter rvStickerAdapterMeep;
    private List<String> listMeep;
//    private ArrayList<ObjSticker> listStickerr;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.fragment_sticker_meep,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        Log.d("aaa","DAvaoroi");
        rvStickerMeep = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.rvStickerMeep);
        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvStickerMeep.setLayoutManager(manager);
        rvStickerMeep.setAdapter(rvStickerAdapterMeep);
        rvStickerAdapterMeep.notifyDataSetChanged();
    }


    @Override
    public void initComponents() {
        listMeep = ManagerSticker.getINSTANCE().getListCCC(getContext());
        rvStickerAdapterMeep = new RVStickerAdapter(getContext(), listMeep);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setEventViews() {

    }
}
