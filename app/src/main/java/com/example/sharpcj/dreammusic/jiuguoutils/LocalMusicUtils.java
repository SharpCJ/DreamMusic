package com.example.sharpcj.dreammusic.jiuguoutils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by joy on 2016/7/18.
 */
public class LocalMusicUtils {
    public static List<MediaStoreSong> getAllLocalSongInfo(Context context){
        List<MediaStoreSong> mlstLocalSong=new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor==null){
            return null;
        }
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            String display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            MediaStoreSong mediaStoreSong=new MediaStoreSong(id,tilte,album,artist,url,display_name,duration,size);
            mlstLocalSong.add(mediaStoreSong);
        }
        cursor.close();
        return mlstLocalSong;
    }

    public static Map<String,List<MediaStoreSong>> getAllSinger(Context context){
        HashMap<String,List<MediaStoreSong>> map=new HashMap<>();
        List<MediaStoreSong> allLocalSongInfo = getAllLocalSongInfo(context);
        List<String> list=new ArrayList<>();
        for(MediaStoreSong song : allLocalSongInfo){
            list.add(song.getArtist());
        }
        HashSet<String> set=new HashSet<>(list);
        String[] artists = (String[]) set.toArray();
        for (int i = 0; i < artists.length; i++) {
            List<MediaStoreSong> values=new ArrayList<>();
            for(MediaStoreSong song : allLocalSongInfo){
                if(artists[i].equals(song.getArtist())){
                    values.add(song);
                }
            }
            map.put(artists[i],values);
        }
        return map;
    }

    public static Map<String,List<MediaStoreSong>> getAllAlbum(Context context){
        HashMap<String,List<MediaStoreSong>> map=new HashMap<>();
        List<MediaStoreSong> allLocalSongInfo = getAllLocalSongInfo(context);
        List<String> list=new ArrayList<>();
        for(MediaStoreSong song : allLocalSongInfo){
            list.add(song.getAlbum());
        }
        HashSet<String> set=new HashSet<>(list);
        String[] albums = (String[]) set.toArray();
        for (int i = 0; i < albums.length; i++) {
            List<MediaStoreSong> values=new ArrayList<>();
            for(MediaStoreSong song : allLocalSongInfo){
                if(albums[i].equals(song.getAlbum())){
                    values.add(song);
                }
            }
            map.put(albums[i],values);
        }
        return map;
    }
}
