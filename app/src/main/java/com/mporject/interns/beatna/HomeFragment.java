package com.mporject.interns.beatna;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    ArrayList<Post> news=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.newsfeed_fragment,container,false);


        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.post_getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();
                        User poster;
                        Album album_p;
                        Song song_p;

                        ListView  news_lv;
                        PostAdapter PA;
                        news_lv=myView.findViewById(R.id.news_lv);
                        for (int i = 0; i < n; ++i) {
                            JSONObject post=posts_data.getJSONObject(i);
                            poster=new User(post.getString("unique_id"),post.getString("name"),post.getString("email"),post.getInt("role"));
                            song_p= new Song(post.getInt("song_id"),post.getString("title"),poster);
                            news.add(new Post(poster,song_p,post.getString("created_at")));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                        PA = new PostAdapter(Objects.requireNonNull(getContext()),R.id.news_lv,news);
                        news_lv.setAdapter(PA);

                    }
                }));

        final EditText search_et=myView.findViewById(R.id.search_et);

        ImageButton search_ib=myView.findViewById(R.id.search_ib);
        search_ib.setOnClickListener(v -> {
            ArrayList<Post> news_search=  news.stream().filter(p->p.getSong().getTitle().contains(search_et.getText())).collect(Collectors.toCollection(ArrayList::new));
          PostAdapter  PA = new PostAdapter(Objects.requireNonNull(getContext()),R.id.news_lv,news_search);
          ListView  news_lv=myView.findViewById(R.id.news_lv);
          news_lv.setAdapter(PA);
        });



        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
