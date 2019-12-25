package com.example.musicplayer.musicplayer;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.musicplayer.musicplayer.fragment.MusicListFragment;
import com.example.musicplayer.musicplayer.fragment.LogicFragment;
import com.example.shaoyangyang.shaoshuaih.R;
import com.example.musicplayer.musicplayer.adapter.MusicPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//实现OnClickListener的接口
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //定义activity_main.xml的控件对象
    private TextView logicTv;      //本地音乐tv
    private TextView musiclistTv;  //我的歌单tv
    private ViewPager viewPager;   //滑动界面
    //将Fragment放入List集合中，存放fragment对象
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定id
        getID();
        //设置监听
        listener();
        //创建fragment对象
        LogicFragment logicFragment = new LogicFragment();
        MusicListFragment MusicListFragment = new MusicListFragment();
        //将fragment对象添加到fragmentList中
        fragmentList.add(logicFragment);
        fragmentList.add(MusicListFragment);
        //通过MusicPagerAdapter类创建musicPagerAdapter的适配器，下面将添加MusicPagerAdapter类的创建方法
        MusicPagerAdapter musicPagerAdapter = new MusicPagerAdapter(getSupportFragmentManager(), fragmentList);
        MusicPagerAdapter musicPagerAdapter2 = new MusicPagerAdapter(getSupportFragmentManager(), fragmentList);
        //viewPager绑定适配器
        viewPager.setAdapter(musicPagerAdapter);
        viewPager.setAdapter(musicPagerAdapter2);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {   //选择，根据选择变色
                    case 0:
                        logicTv.setTextColor(getResources().getColor(R.color.white));
                        musiclistTv.setTextColor(getResources().getColor(R.color.white_60P));
                        break;
                    case 1:
                        musiclistTv.setTextColor(getResources().getColor(R.color.white));
                        logicTv.setTextColor(getResources().getColor(R.color.white_60P));
                        break;
                }

            }// change color

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void listener() {
        logicTv.setOnClickListener(this);
        musiclistTv.setOnClickListener(this);
        // menuImagv.setOnClickListener(this);
        // seachImagv.setOnClickListener(this);
    }

    private void getID() {
        logicTv = findViewById(R.id.main_logic_tv);
        musiclistTv = findViewById(R.id.main_musiclist_tv);
        viewPager = findViewById(R.id.main_vp);
        //menuImagv = findViewById(R.id.main_menu_imgv);
        //seachImagv = findViewById(R.id.main_search_imgv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_logic_tv:
                //实现点击TextView切换fragment
                viewPager.setCurrentItem(0);//制定第一个界面为当前界面
                break;
            case R.id.main_musiclist_tv:
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }

    }


   /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            final View view = LayoutInflater.from(this).inflate(R.layout.dialog1_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出提示");
            builder.setView(view);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("算了吧", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "hahahhaha", Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/
}

