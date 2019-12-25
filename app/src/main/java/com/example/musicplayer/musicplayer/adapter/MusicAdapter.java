package com.example.musicplayer.musicplayer.adapter;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.musicplayer.DataBase.DBGedan;
import com.example.musicplayer.musicplayer.MainActivity;
import com.example.musicplayer.musicplayer.utils.Common;
import com.example.shaoyangyang.shaoshuaih.R;
import com.example.musicplayer.musicplayer.entity.Music;

import java.util.List;

/**
 *
 */
//继承BaseAdapter 实现自定义适配器，复写BaseAdapter的代码
public class MusicAdapter extends BaseAdapter {
    //定义两个属性，用List集合存放Music类
    private Context context;
    private List<Music> musicList;

    //创建MusicAdapter的构造方法，在LogicFragment需要调用MusicAdapter的构造方法来创建适配器
    public MusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;

    }

    //这里需要返回musicList.size()
    @Override
    public int getCount() {
        return Common.musicList.size();
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
        ViewHolder viewHolder = null;
        //缓存原理，程序运行到这里判断convertView是否为空
        if (convertView == null) {
            //绑定行布局文件，就是绑定需要适配的模板
            view = LayoutInflater.from(context).inflate(R.layout.music_item, null);
            //实例化ViewHolder
            viewHolder = new ViewHolder();  //Viewholder对象它一般包括listview子项里所有的组件，
                                        // convertView是空的，在Viewholder里存储对列表子项每个组件的id应用
            viewHolder.titleTv = view.findViewById(R.id.musicitem_title_tv);
            viewHolder.artistTv = view.findViewById(R.id.musicitem_artist_tv);
            //viewHolder.isPlayingView = view.findViewById(R.id.musicitem_playing_v);
            viewHolder.add_menu = view.findViewById(R.id.add_menu);
            view.setTag(viewHolder);//过setTag方法，把这个带有view引用的对象附加在View上

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//通过getTag直接从view携带的Viewholder中取出每个组件的引用
        }
        //赋值 准确的是绑定赋值的中介
        viewHolder.titleTv.setText(Common.musicList.get(position).title);
        viewHolder.artistTv.setText(Common.musicList.get(position).artist);
        /*if (Common.musicList.get(position).isPlaying) {
            viewHolder.isPlayingView.setVisibility(View.VISIBLE);//设置为显示
        } else {
            viewHolder.isPlayingView.setVisibility(View.INVISIBLE);//设置为不显示但占用空间
        }*/
        viewHolder.add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem(position);
            }
        });



        return view;
    }

    //创建一个类ViewHolder，用来存放music_item.xml中的控件
    class ViewHolder {
        TextView titleTv;
        TextView artistTv;
        //View isPlayingView;
        TextView add_menu;
    }
    private void AddItem(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("增加提醒");
        builder.setMessage("您确定要加入歌单吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String title = Common.musicList.get(position).title;
                String artist = Common.musicList.get(position).artist;
                int length  = Common.musicList.get(position).length;
                String path = Common.musicList.get(position).path;


                DBGedan dbHelper=new DBGedan(context,"music_db",null,1);
                SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("songname",title);
                values.put("singer",artist);
                values.put("length",length);
                values.put("path",path);
                sqLiteDatabase.insert("music",null,values);
                //Toast.makeText(context, "musicadp", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();//显示对话框
    }
}
