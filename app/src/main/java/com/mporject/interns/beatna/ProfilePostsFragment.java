package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfilePostsFragment extends Fragment {
    public ProfilePostsFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.profile_posts_fragment,container,false);

        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.getPostByIdUser("55a0ca87-42ae-4151-9c9f-80dc1127276c")
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
                        ArrayList<Post> news=new ArrayList<>();
                        ListView news_lv;
                        PostAdapter PA;
                        news_lv=myView.findViewById(R.id.userposts_lv);
                        for (int i = 0; i < n; ++i) {
                            JSONObject post=posts_data.getJSONObject(i);
                            poster=new User(post.getString("unique_id"),post.getString("name"),post.getString("email"),post.getInt("role"));
                            song_p= new Song(post.getInt("song_id"),post.getString("title"),poster);
                            news.add(new Post(poster,song_p,post.getString("created_at")));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                        PA = new PostAdapter(Objects.requireNonNull(getContext()),R.id.userposts_lv,news);
                        news_lv.setAdapter(PA);

                    }
                }));
        return myView;
    }
}
