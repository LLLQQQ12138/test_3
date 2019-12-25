package com.example.musicplayer.musicplayer.fragment;


import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.musicplayer.musicplayer.DataBase.DBGedan;
import com.example.musicplayer.musicplayer.utils.Common;
import com.example.musicplayer.musicplayer.MusicActivity;

import com.example.musicplayer.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.musicplayer.entity.Music;
import com.example.shaoyangyang.shaoshuaih.R;
import com.master.permissionhelper.PermissionHelper;

import java.util.List;

import static com.example.musicplayer.musicplayer.utils.Common.musicList1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends Fragment {
    private String TAG = "Hello";                                       //下面两个属性和获取mediadatabase的权限有关系，可查阅代码块下的链接
    private PermissionHelper permissionHelper;
    private ListView listView1;                                                          //创建ListView的对象
    private List<Music> musicList2;                                                          //将Music放入List集合中，并实例化List<Music>
    private List<ListView> listViewList;
    private MusicListAdapter adapter;


    public MusicListFragment() {
        // Required empty public constructor
    }

    //LoginFrangment中的onCreate()方法
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gedan, container, false);        //创建View对象，返回view
        initListView2();



        //对Listview进行监听
        listView1 = view.findViewById(R.id.musiclist_lv);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {             //将listView的每一个item实现监听
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Music m : musicList1)
                {
                    m.isPlaying = false;
                }
                musicList1.get(position).isPlaying = true;
                //更新界面
                adapter.notifyDataSetChanged();
                //intent实现页面的跳转，getActivity()获取当前的activity， MusicActivity.class将要调转的activity
                Intent intent = new Intent(getActivity(), MusicActivity.class);
                //使用putExtra（）传值
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

        adapter = new MusicListAdapter(getActivity(), musicList1);                //创建MusicListAdapter的对象，实现自定义适配器的创建
        listView1.setAdapter(adapter);                                                 //listView绑定适配器
        return view;
    }

    private void initListView() {
        musicList1.clear();//清空list
        //获取ContentResolver的对象，并进行实例化
        ContentResolver resolver = getActivity().getContentResolver();
        //获取游标
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER); //创建游标MediaStore.Audio.Media.EXTERNAL_CONTENT_URI获取音频的文件，后面的是关于select筛选条件，这里填null就可以了
        //游标归零
        if(cursor.moveToFirst()){
            do {                                                                                         //从系统数据库中
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));    //获取歌名
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));  //获取歌唱者
                int length = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                //创建Music对象，并赋值
                Music music = new Music();
                music.length = length;
                music.title = title;
                music.artist = artist;
                music.path = path;

                //将music放入musicList集合中
                musicList1.add(music);
            }  while (cursor.moveToNext());
        }else {
            Toast.makeText(getActivity(), "本地无音乐", Toast.LENGTH_SHORT).show();
        }
        cursor.close();                                                                         //关闭游标
    }

    //nitListView()实现对手机中MediaDataBase的扫描
    private void initListView2() {
        musicList1.clear();
        DBGedan dbHelper=new DBGedan(getContext(),"music_db",null,1);
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        //获取游标
        Cursor cursor=sqLiteDatabase.query("music",null,null,null,null,null,null);
        //游标归零
        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex("songname"));            //获取歌名
                String artist = cursor.getString(cursor.getColumnIndex("singer"));         //获取歌唱者
                int length = cursor.getInt(cursor.getColumnIndex("length"));
                String path = cursor.getString(cursor.getColumnIndex("path"));
                //创建Music对象，并赋值
                Music music = new Music();
                music.length = length;
                music.title = title;
                music.artist = artist;
                music.path = path;
                //将music放入musicList集合中
                musicList1.add(music);
            }  while (cursor.moveToNext());
        }else {
            Toast.makeText(getActivity(), "本地无音乐", Toast.LENGTH_SHORT).show();
        }
        cursor.close();                                                                      //关闭游标
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}


