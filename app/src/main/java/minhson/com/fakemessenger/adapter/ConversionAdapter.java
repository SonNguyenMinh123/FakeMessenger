package minhson.com.fakemessenger.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import minhson.com.fakemessenger.R;
import minhson.com.fakemessenger.database.SaveMessengerDataBaseMgr;
import minhson.com.fakemessenger.item.ChatMessage;

/**
 * Created by Administrator on 6/8/2017.
 */

public class ConversionAdapter extends RecyclerView.Adapter<ConversionAdapter.MyViewHolder> {
    private Context context;
    private DeleteItem deleteItem;
    private String url;
    private int id;
    private SaveMessengerDataBaseMgr saveMessengerDataBaseMgr = new SaveMessengerDataBaseMgr();
    private ChatMessage chatMessage = new ChatMessage();
    private ArrayList<ChatMessage> listChat = new ArrayList<>();
    private int color;
    private GradientDrawable gd;
    private String uri;
    private BroadcastReceiver receiverColor;

    public ConversionAdapter(ArrayList<ChatMessage> listChat, Context context, String url
            , DeleteItem deleteItem, int id) {
        this.listChat = listChat;
        this.context = context;
        this.url = url;
        this.deleteItem = deleteItem;
        this.id = id;

    }

    public void setDeleteItem(DeleteItem deleteItem) {
        this.deleteItem = deleteItem;
    }

    public void setListChat(ArrayList<ChatMessage> listChat) {
        this.listChat = listChat;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                LayoutInflater inflater = LayoutInflater.from(context);
                View view1 = inflater.inflate(R.layout.item_chat_type_1, parent, false);
                return new MyViewHolderType1(view1);

            case 2:
                LayoutInflater inflater2 = LayoutInflater.from(context);
                View view2 = inflater2.inflate(R.layout.item_chat_type_2, parent, false);
                return new MyViewHolderType2(view2);

            case 3:
                LayoutInflater inflater3 = LayoutInflater.from(context);
                View view3 = inflater3.inflate(R.layout.item_chat_type_3, parent, false);
                return new MyViewHolderType3(view3);

            case 4:
                LayoutInflater inflater4 = LayoutInflater.from(context);
                View view4 = inflater4.inflate(R.layout.item_chat_type_4, parent, false);
                return new MyViewHolderType4(view4);

            case 5:
                LayoutInflater inflater5 = LayoutInflater.from(context);
                View view5 = inflater5.inflate(R.layout.item_chat_type_5, parent, false);
                return new MyViewHolderType5(view5);

            case 6:
                LayoutInflater inflater6 = LayoutInflater.from(context);
                View view6 = inflater6.inflate(R.layout.item_chat_type_6, parent, false);
                return new MyViewHolderType6(view6);

            case 7:
                LayoutInflater inflater7 = LayoutInflater.from(context);
                View view7 = inflater7.inflate(R.layout.item_chat_type_7, parent, false);
                return new MyViewHolderType7(view7);

            case 8:
                LayoutInflater inflater8 = LayoutInflater.from(context);
                View view8 = inflater8.inflate(R.layout.item_chat_type_8, parent, false);
                return new MyViewHolderType8(view8);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ChatMessage chatMrg = listChat.get(position);
        uri = chatMrg.getUriAvatar();
        saveMessengerDataBaseMgr.copyDatabase(context);
        int type = listChat.get(position).getType();
        int size = listChat.size();

        switch (listChat.get(position).getType()) {
            case 1:
                MyViewHolderType1 viewholderT1 = (MyViewHolderType1) holder;
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        Log.e("default", "" + uri);
                        viewholderT1.ivType1.setImageResource(R.drawable.icon_user_default);
                        viewholderT1.ivType1.setImageResource(R.drawable.icon_user_default);

                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT1.ivType1);
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT1.iv_type_1);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                viewholderT1.tvType1.setText(chatMrg.getBody());
                viewholderT1.tvType1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });
                //
                if (size == 1) {
                    if (listChat.get(position).getType() % 2 != 0) {
                        viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact);
                        viewholderT1.flType1.setVisibility(View.VISIBLE);
                        viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                    }
                } else if (type % 2 != 0) {
                    if ((position - 1) >= 0 && (position + 1) < size) {
                        if (listChat.get(position - 1).getType() % 2 != 0
                                && listChat.get(position + 1).getType() % 2 != 0) {
                            Log.e("AAAA", listChat.get(position).getBody() + ":O giua");
                            viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact_2);
                            viewholderT1.flType1.setVisibility(View.INVISIBLE);
                            viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                        } else if (listChat.get(position - 1).getType() % 2 == 0
                                && listChat.get(position + 1).getType() % 2 != 0) {
                            Log.e("AAAA", listChat.get(position).getBody() + ":Bat dau mot chuoi hoi thoai");
                            viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact_1);
                            viewholderT1.flType1.setVisibility(View.INVISIBLE);
                            viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                        } else if (listChat.get(position - 1).getType() % 2 != 0
                                && listChat.get(position + 1).getType() % 2 == 0) {
                            Log.e("AAAA", listChat.get(position).getBody() + ":Ket thuc mot chuoi hoi thoai");
                            viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact_3);
                            viewholderT1.flType1.setVisibility(View.VISIBLE);
                            viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                        } else {
                            Log.e("AAAA", listChat.get(position).getBody() + ":Tin nhan don default");
                            viewholderT1.flType1.setVisibility(View.VISIBLE);
                            viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                            viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact);
                        }

                    } else {
                        if ((position + 1) == size) {
                            if (listChat.get(position - 1).getType() % 2 != 0) {
                                Log.e("AAAA", position + ":Tin nhan ket thuc");
                                viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact_3);
                                viewholderT1.flType1.setVisibility(View.VISIBLE);
                                viewholderT1.iv_type_1.setVisibility(View.VISIBLE);
                            } else {
                                Log.e("AAAA", listChat.get(position).getBody() + ":Tin nhan don default");
                                viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact);
                                viewholderT1.flType1.setVisibility(View.VISIBLE);
                                viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                            }
                        }
                        if ((position) == 0) {
                            if (listChat.get(position + 1).getType() % 2 != 0) {
                                Log.e("AAAA", listChat.get(position).getBody() + ":Tin nhan dau tien");
                                viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact_1);
                                viewholderT1.flType1.setVisibility(View.INVISIBLE);
                                viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                            } else {
                                Log.e("AAAA", listChat.get(position).getBody() + ":Tin nhan don default");
                                viewholderT1.tvType1.setBackgroundResource(R.drawable.bg_chat_contact);
                                viewholderT1.flType1.setVisibility(View.VISIBLE);
                                viewholderT1.iv_type_1.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                } else {

                }

                break;

            case 2:
                final MyViewHolderType2 viewholderT2 = (MyViewHolderType2) holder;
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT2.ivType2.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT2.ivType2);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                viewholderT2.tvType2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });
                //
                if (size == 1) {
                    viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you);
                    Log.e("DUNGNN", "CHI CÓ 1 phần tử");
                } else if (type % 2 == 0) {
                    if ((position - 1) >= 0 && (position + 1) < size) {
                        if (listChat.get(position - 1).getType() % 2 == 0
                                && listChat.get(position + 1).getType() % 2 == 0) {
                            Log.e("DUNGNN", listChat.get(position).getBody() + ":O giua");
                            viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you_2);
                            viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                        } else if (listChat.get(position - 1).getType() % 2 != 0
                                && listChat.get(position + 1).getType() % 2 == 0) {
                            Log.e("DUNGNN", listChat.get(position).getBody() + ":Bat dau mot chuoi hoi thoai");
                            viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you_1);
                            viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                        } else if (listChat.get(position - 1).getType() % 2 == 0
                                && listChat.get(position + 1).getType() % 2 != 0) {
                            Log.e("DUNGNN", listChat.get(position).getBody() + ":Ket thuc mot chuoi hoi thoai");
                            viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you_3);
                            viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                        } else {
                            Log.e("DUNGNN", listChat.get(position).getBody() + ":Tin nhan don default");
                            viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you);
                            viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                        }

                    } else {
                        if ((position + 1) == size) {
                            if (listChat.get(position - 1).getType() % 2 == 0) {
                                Log.e("DUNGNN", listChat.get(position).getBody() + ":Tin nhan ket thuc");
                                viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you_3);
                                viewholderT2.ivType2.setVisibility(View.VISIBLE);
                            } else {
                                Log.e("DUNGNN", listChat.get(position).getBody() + ":Tin nhan don default");
                                viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you);
                                viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                            }
                        }
                        if (position == 0) {
                            if (listChat.get(position + 1).getType() % 2 == 0) {
                                Log.e("DUNGNN", listChat.get(position).getBody() + ":Tin nhan dau tien");
                                viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you_1);
                                viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                            } else {
                                Log.e("DUNGNN", listChat.get(position).getBody() + ":Tin nhan don default");
                                viewholderT2.tvType2.setBackgroundResource(R.drawable.bg_chat_you);
                                viewholderT2.ivType2.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                } else {

                }
                viewholderT2.tvType2.setText(chatMrg.getBody());

                break;

            case 3:
                MyViewHolderType3 viewholderT3 = (MyViewHolderType3) holder;
                String uriStr = listChat.get(position).getUriAvatar();
                String uriGallery = listChat.get(position).getIsMe();
                //icons
                try {

                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT3.ivIconType3.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT3.ivIconType3);
                    }
                    //gallery
                    Glide.with(context).load(Uri.parse(chatMrg.getIsMe())).into(viewholderT3.ivType3);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                viewholderT3.ivType3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });
                break;

            case 4:
                MyViewHolderType4 viewholderT4 = (MyViewHolderType4) holder;
                String uriStrRight4 = listChat.get(position).getUriAvatar();
                String uriGallery4 = listChat.get(position).getIsMe();
                //icon
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT4.ivIconType4.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT4.ivIconType4);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //gallery
                Glide.with(context).load(Uri.parse(chatMrg.getIsMe())).into(viewholderT4.ivType4);

                viewholderT4.ivType4.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });
                //
                if (listChat.get(position).getType() == 4) {
                    if (saveMessengerDataBaseMgr.getMessage(id).size() != 0) {
                        chatMessage = saveMessengerDataBaseMgr.getLastPositionChat(id);
                        if (chatMessage.getBody() != null) {

                        } else if (chatMessage.getIsMe() != null) {
                            String lastPos = chatMessage.getIsMe();
                            if (viewholderT4.ivType4.equals(lastPos)) {
                                viewholderT4.ivIconType4.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                break;

            case 5:
                MyViewHolderType5 viewholderT5 = (MyViewHolderType5) holder;
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT5.ivIconType5.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT5.ivIconType5);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //sticker
                try {
                    InputStream is = context.getAssets().open(chatMrg.getSticker());
                    Drawable d = Drawable.createFromStream(is, null);
                    viewholderT5.ivType5.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                viewholderT5.ivType5.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });

                break;

            case 6:
                MyViewHolderType6 viewholderT6 = (MyViewHolderType6) holder;
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT6.ivIconType6.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT6.ivIconType6);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                // sticker
                try {
                    InputStream is = context.getAssets().open(chatMrg.getSticker());
                    Drawable d = Drawable.createFromStream(is, null);
                    viewholderT6.ivType6.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                viewholderT6.ivType6.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });

                break;

            case 7:
                MyViewHolderType7 viewholderT7 = (MyViewHolderType7) holder;
                InputStream imageStream7 = null;
                viewholderT7.ivType7.setImageResource(listChat.get(position).getLike());
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT7.ivIconType7.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT7.ivIconType7);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                viewholderT7.ivType7.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });

                break;

            case 8:
                MyViewHolderType8 viewholderT8 = (MyViewHolderType8) holder;
                InputStream imageStream8 = null;
                viewholderT8.ivType8.setImageResource(listChat.get(position).getLike());
                try {
                    if (uri.equals(String.valueOf(R.drawable.icon_user_default))) {
                        viewholderT8.ivIconType8.setImageResource(R.drawable.icon_user_default);
                    } else {
                        Glide.with(context).load(Uri.parse(uri)).asBitmap().into(viewholderT8.ivIconType8);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
//                try {
//                    imageStream8 = context.getContentResolver().
//                            openInputStream(Uri.parse(chatMrg.getUriAvatar()));
//                    Bitmap tmpAvatar = BitmapFactory.decodeStream(imageStream8);
//                    viewholderT8.ivIconType8.setImageBitmap(tmpAvatar);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                viewholderT8.ivType8.setColorFilter(color);
                viewholderT8.ivType8.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        deleteItem.deleted(chatMrg.getId());
                        return false;
                    }
                });

                break;

            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (listChat.get(position).getType()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;

            default:
                return 1;
        }
    }

    public interface DeleteItem {
        void deleted(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View vContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
        }
    }

    //type 1: text off contact
    public class MyViewHolderType1 extends MyViewHolder {
        TextView tvType1;
        ImageView ivType1;
        ImageView ivMes;
        FrameLayout flType1;
        ImageView iv_type_1;

        public MyViewHolderType1(View itemView) {
            super(itemView);
            tvType1 = (TextView) itemView.findViewById(R.id.tvSmsType1);
            ivType1 = (ImageView) itemView.findViewById(R.id.ivAvatarType1);
            ivMes = (ImageView) itemView.findViewById(R.id.iv_mes);
            flType1 = (FrameLayout) itemView.findViewById(R.id.rl_type1);
            iv_type_1 = (ImageView) itemView.findViewById(R.id.iv_type_1);
        }
    }

    //type 2: text off you
    public class MyViewHolderType2 extends MyViewHolder {
        TextView tvType2;
        ImageView ivType2;
        TextView tvType2Gone;

        public MyViewHolderType2(View itemView) {
            super(itemView);
            tvType2 = (TextView) itemView.findViewById(R.id.tvSmsType2);
            ivType2 = (ImageView) itemView.findViewById(R.id.ivAvatarType2);
            tvType2Gone = (TextView) itemView.findViewById(R.id.tv_type_2);
        }
    }

    //type 3: image off contact
    public class MyViewHolderType3 extends MyViewHolder {
        CircleImageView ivIconType3;
        ImageView ivType3;

        public MyViewHolderType3(View itemView) {
            super(itemView);
            ivIconType3 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType3);
            ivType3 = (ImageView) itemView.findViewById(R.id.ivSmsType3);
        }
    }

    //type 4: image off you
    public class MyViewHolderType4 extends MyViewHolder {
        RoundedImageView ivType4;
        CircleImageView ivIconType4;

        public MyViewHolderType4(View itemView) {
            super(itemView);
            ivType4 = (RoundedImageView) itemView.findViewById(R.id.ivSmsType4);
            ivIconType4 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType4);
        }
    }

    //type 5: icons off contact
    public class MyViewHolderType5 extends MyViewHolder {
        RoundedImageView ivType5;
        CircleImageView ivIconType5;

        public MyViewHolderType5(View itemView) {
            super(itemView);
            ivType5 = (RoundedImageView) itemView.findViewById(R.id.ivSmsType5);
            ivIconType5 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType5);
        }
    }

    //type 6: icons off you
    public class MyViewHolderType6 extends MyViewHolder {
        RoundedImageView ivType6;
        CircleImageView ivIconType6;

        public MyViewHolderType6(View itemView) {
            super(itemView);
            ivType6 = (RoundedImageView) itemView.findViewById(R.id.ivSmsType6);
            ivIconType6 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType6);
        }
    }

    //type 7: icons like off contact
    public class MyViewHolderType7 extends MyViewHolder {
        RoundedImageView ivType7;
        CircleImageView ivIconType7;

        public MyViewHolderType7(View itemView) {
            super(itemView);
            ivType7 = (RoundedImageView) itemView.findViewById(R.id.ivSmsType7);
            ivIconType7 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType7);
        }
    }

    //type 8: icons like off you
    public class MyViewHolderType8 extends MyViewHolder {
        RoundedImageView ivType8;
        CircleImageView ivIconType8;

        public MyViewHolderType8(View itemView) {
            super(itemView);
            ivType8 = (RoundedImageView) itemView.findViewById(R.id.ivSmsType8);
            ivIconType8 = (CircleImageView) itemView.findViewById(R.id.ivIconsSmsType8);
        }
    }
}
