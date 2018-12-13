package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    TextView txtNameAlbum;
    TextView txtArtistName;
    ImageView imgChill,imgSad,imgHappy,imgStudying,imgMotivation,imgGaming,imgSleepy, imgLove;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View myView=inflater.inflate(R.layout.fragment_playlist,container,false);
        imgChill=myView.findViewById(R.id.imgChill);
        imgGaming=myView.findViewById(R.id.imgGaming);
        imgStudying=myView.findViewById(R.id.imgStudying);
        imgHappy=myView.findViewById(R.id.imgHappy);
        imgSad=myView.findViewById(R.id.imgSad);
        imgMotivation=myView.findViewById(R.id.imgMotivation);
        imgLove=myView.findViewById(R.id.imgLove);
        imgSleepy=myView.findViewById(R.id.imgSleepy);



        imgChill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Chill");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("aaaaaaaaaa");
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Happy");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Sad");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Love");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgStudying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Studying");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgMotivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Motivational");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgGaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Gaming");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        imgSleepy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodPlaylistFragment moodFra= new MoodPlaylistFragment();
                Bundle bundle= new Bundle();
                bundle.putString("mood","Sleepy");
                moodFra.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,moodFra).addToBackStack(null).commit();
            }
        });

        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* txtNameAlbum=(TextView) getView().findViewById(R.id.txtAlbumName);
        txtArtistName=(TextView) getView().findViewById(R.id.txtTrackName);

        myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        final ListView listALbum=(ListView) view.findViewById(R.id.listTracks);
        final ArrayList<String> album=new ArrayList<>();
        compositeDisposable.add(myAPI.displaySongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                  @Override
                    public void accept(String s) throws JSONException {
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
        compositeDisposable.add(myAPI.getAlbumInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws JSONException {
                        final JSONArray geodata = new JSONArray(s);
                        final int n = geodata.length();
                        for (int i = 0; i < n; ++i) {
                            final JSONObject infos = geodata.getJSONObject(i);
                            txtArtistName.setText(infos.getString("name"));
                            txtNameAlbum.setText(infos.getString("title"));
                        }
                    }

                }));
*/

    }


}
