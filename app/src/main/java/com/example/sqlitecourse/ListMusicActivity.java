package com.example.sqlitecourse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.security.Permission;
import java.util.ArrayList;

public class ListMusicActivity extends AppCompatActivity {

    ArrayList<Song> songArrayList;
    ListView lvSongs;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_music);

        lvSongs = findViewById(R.id.lvSongs);
        songArrayList = new ArrayList<>();

        SongsAdapter songsAdapter = new SongsAdapter(this,R.layout.item_song,songArrayList);
        lvSongs.setAdapter(songsAdapter);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        } else {
            getSounds();
        }


        songsAdapter.notifyDataSetChanged();

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListMusicActivity.this,MainActivity.class);

                intent.putExtra("song",songArrayList.get(position));

                /* a la place de faire tout ca on peut envoyer directement un objet
                song sans avoir envoyer toutes ces composantes en implimentant l'interface
                Serializable dans la classe song
                 */

               /* intent.putExtra("title",songArrayList.get(position).getTitle());
                intent.putExtra("artist",songArrayList.get(position).getArtist());
                intent.putExtra("path",songArrayList.get(position).getPath());*/
                startActivity(intent);
            }
        });

    }





    private void getSounds() { ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor;
        songCursor = contentResolver.query(songUri, null, null, null,null);
        if (songCursor!=null && songCursor.moveToFirst())
        {
            do {
                String title = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String path = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                Song song =new Song(title,artist,path);
                songArrayList.add(song);
            }while (songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSounds();
            }
        }
    }
}
