package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import io.reactivex.Observable;
import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PlaylistFragment extends Fragment {

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private NodeJS myAPI;
    private CompositeDisposable compositeDisposable= new CompositeDisposable();
    List<Song> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.album_interface,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myAPI=SignInFragment.Companion.getRetrofit().create(NodeJS.class);
        final ListView listALbum=(ListView) view.findViewById(R.id.listTracks);
        final ArrayList<String> album=new ArrayList<>();
        compositeDisposable.add(myAPI.displaySongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                  @Override
                    public void accept(String s) throws JSONException {
                      /*  String[] sWthtComma=s.split("\\,");
                        //System.out.println("cuts0="+cutS[0]);
                       // System.out.println("cuts1="+cutS[1]);
                        //System.out.println("cuts2="+cutS[2]);
                        //System.out.println("cuts3="+cutS[3]);
                      Song firstSongInList = new Song();

                      String firstsongTitle=sWthtComma[0].substring(1);
                      String lastsongTile=sWthtComma[sWthtComma.length].substring(0,sWthtComma[sWthtComma.length].length()-1);

                      firstSongInList.settitle(firstsongTitle);
                      album.add(firstSongInList);
                      for(int i=1;i<sWthtComma.length-2;i++) {
                          System.out.println("s=" + s);
                          Song song1 = new Song();
                          song1.settitle(sWthtComma[i]);
                          album.add(song1);
                          AlbumAdapter adapter = new AlbumAdapter(getActivity(), R.layout.list_album, album);
                          listALbum.setAdapter(adapter);
                      }
                      Song lastSongInList = new Song();
                      firstSongInList.settitle(lastsongTile);
                      album.add(lastSongInList);*/

                      final JSONArray geodata = new JSONArray(s);
                      final int n = geodata.length();
                      for (int i = 0; i < n; ++i) {
                          final JSONObject songs = geodata.getJSONObject(i);
                          //System.out.println(songs.getInt("id"));
                          System.out.println(songs.getString("title"));
                          album.add(songs.getString("title"));
                          AlbumAdapter adapter = new AlbumAdapter(getActivity(), R.layout.list_album, album);
                          listALbum.setAdapter(adapter);
                      }
                  }

                }));

    }


}
