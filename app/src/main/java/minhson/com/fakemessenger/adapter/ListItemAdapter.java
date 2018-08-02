package minhson.com.fakemessenger.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.dialog.DeleteContactDialog;
import minhson.com.fakemessenger.item.ChatMessage;
import minhson.com.fakemessenger.item.ItemFakeChat;

/**
 * Created by Administrator on 22/7/2017.
 */

public class ListItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ItemFakeChat> listItem = new ArrayList<>();
    private onClickListener clicked;
    private String uriStr;
    private DeleteContactDialog deleteContactDialog;
    private String lastPos;
    private ArrayList<ChatMessage> listChat = new ArrayList<>();
    private SaveMessengerDataBaseMgr baseMgr = new SaveMessengerDataBaseMgr();
    private ChatMessage chatMessage = new ChatMessage();

    public ListItemAdapter(Context context, ArrayList<ItemFakeChat> listItemm) {
        this.context = context;
        this.listItem = listItemm;
    }

    public void setClicked(onClickListener clicked) {
        this.clicked = clicked;
    }

    public void setListItem(ArrayList<ItemFakeChat> listItem) {
        this.listItem = listItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
        RecyclerView.LayoutParams layoutParams =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        view.setLayoutParams(layoutParams);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ListHolder listHolder = (ListHolder) holder;
        ItemFakeChat itemFakeChat = listItem.get(position);
        uriStr = itemFakeChat.getAvatar();
        lastPos = itemFakeChat.getMessenger();

        listHolder.tv_contact.setText(listItem.get(position).getContact());
        listHolder.tv_messenger.setText(listItem.get(position).getMessenger());
        listHolder.tv_date.setText(listItem.get(position).getDate());
        listHolder.rlitem.setId(listItem.get(position).getId());

        if (uriStr.equals(String.valueOf(R.drawable.icon_user_default))) {
            Log.e("vavavv", "default");
            listHolder.iv_avatar.setImageResource(R.drawable.icon_user_default);

        } else {
            Glide.with(context).load(Uri.parse(uriStr)).asBitmap().into(listHolder.iv_avatar);
            Log.e("vavavv", uriStr);
        }


        listHolder.rlitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteContactDialog = new DeleteContactDialog(context
                        , listItem.get(position).getId()
                        , listItem.get(position).getContact()
                        , uriStr
                        , position);
                deleteContactDialog.show();
                return false;
            }
        });

        listHolder.rlitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked.selected(position, uriStr);
            }
        });
        //
        baseMgr.copyDatabase(context);
        if (listItem.size() != 0) {
            if (baseMgr.getMessage(listItem.get(position).getId()).size() != 0) {
                chatMessage = baseMgr.getLastPositionChat(listItem.get(position).getId());
                if (chatMessage.getBody() != null) {
                    lastPos = chatMessage.getBody();
                    Log.e("isMe", chatMessage.getIsMe());
                    if (lastPos.equals("You are now connected on Messenger")) {

                    } else {
                        listHolder.tv_messenger.setText(lastPos);
                    }
                } else if (chatMessage.getIsMe() != null){
                    listHolder.tv_messenger.setText(context.getResources().getString(R.string.add_a_photo));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public interface onClickListener {
        void selected(int pos, String url);
    }

    private class ListHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_contact;
        private TextView tv_messenger;
        private TextView tv_date;
        private RelativeLayout rlitem;

        public ListHolder(View itemView) {
            super(itemView);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_item_user);
            tv_contact = (TextView) itemView.findViewById(R.id.tv_contact);
            tv_messenger = (TextView) itemView.findViewById(R.id.tv_messenger);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            rlitem = (RelativeLayout) itemView.findViewById(R.id.rl_item);

        }
    }
}
