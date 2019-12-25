package com.example.musicplayer.musicplayer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.musicplayer.DataBase.DBGedan;
import com.example.musicplayer.musicplayer.utils.Common;
import com.example.shaoyangyang.shaoshuaih.R;
import com.example.musicplayer.musicplayer.entity.Music;

import java.util.List;

public class MusicListAdapter extends BaseAdapter {
    //定义两个属性，用List集合存放Music类
    private Context context;
    private List<Music> musicList;

    //创建MusicAdapter的构造方法，在LogicFragment需要调用MusicAdapter的构造方法来创建适配器
    public MusicListAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;

    }

    //这里需要返回musicList.size()
    @Override
    public int getCount() {
        return Common.musicList1.size();
    }

    @Override
    public Object getItem(final int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //在getView（）方法中是实现对模板的绑定，赋值
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //声明View和ViewHolder的对象
        View view = null;
        MusicListAdapter.ViewHolder viewHolder = null;
        //缓存原理，程序运行到这里判断convertView是否为空
        if (convertView == null) {
            //绑定行布局文件，就是绑定需要适配的模板
            view = LayoutInflater.from(context).inflate(R.layout.music_item2, null);
            //实例化ViewHolder
            viewHolder = new MusicListAdapter.ViewHolder();
            viewHolder.titleTv2 = view.findViewById(R.id.musicitem_title_tv2);
            viewHolder.artistTv2 = view.findViewById(R.id.musicitem_artist_tv2);
            //viewHolder.isPlayingView2 = view.findViewById(R.id.musicitem_playing_v2);
            viewHolder.del_menu = view.findViewById(R.id.add_menu2);
            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (MusicListAdapter.ViewHolder) view.getTag();
        }
        //赋值 准确的是绑定赋值的中介
        viewHolder.titleTv2.setText(Common.musicList1.get(position).title);
        viewHolder.artistTv2.setText(Common.musicList1.get(position).artist );
        /*if (Common.musicList.get(position).isPlaying) {
            viewHolder.isPlayingView2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.isPlayingView2.setVisibility(View.INVISIBLE);
        }*/
        viewHolder.del_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });



        return view;
    }

    //创建一个类ViewHolder，用来存放music_item.xml中的控件
    class ViewHolder {
        TextView titleTv2;
        TextView artistTv2;
        //View isPlayingView2;
        TextView del_menu;
    }
    private void deleteItem(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("删除提醒");
        builder.setMessage("您确定要从歌单中删除吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除语句
                DBGedan dbHelper=new DBGedan(context,"music_db",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                database.delete("music","songname=?",new String[]{Common.musicList1.get(position).getTitle()});
                database.delete("music","singer=?",new String[]{Common.musicList1.get(position).getArtist()});
                database.delete("music","length=?",new String[]{String.valueOf(Common.musicList1.get(position).getLength())});
                database.delete("music","path=?",new String[]{Common.musicList1.get(position).getPath()});
                Common.musicList1.remove(position);

                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
