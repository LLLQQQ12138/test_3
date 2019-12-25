package com.example.musicplayer.musicplayer.entity;

import android.graphics.Bitmap;
public class Music {
    /**
     * 在这里所有的属性都是用public修饰的，所以在以后调用时直接调用
     * 如果用private修饰是需要构建set和get方法
     */
    //歌名
    public String title;
    //歌唱者
    public String artist;
    //歌长
    public  int length;

    public String path;
    public boolean isPlaying;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getLength() {
        return length;
    }

    public String getPath() {
        return path;
    }

    public boolean isPlaying() {
        return isPlaying;
    }


}
