package com.mporject.interns.beatna;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class AlbumInterface extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_interface);

        ListView listALbum=(ListView) findViewById(R.id.listTracks);
        ArrayList<String> album=new ArrayList<>();


        AlbumAdapter adapter = new AlbumAdapter(this, R.layout.list_album,album);
        listALbum.setAdapter(adapter);

    }

}
