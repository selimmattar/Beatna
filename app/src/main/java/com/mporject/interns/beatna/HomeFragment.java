package com.mporject.interns.beatna;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.newsfeed_fragment,container,false);
        Session session= new Session(getContext());
        FloatingActionButton Add_FB=myView.findViewById(R.id.Add_FB);
Add_FB.setOnClickListener(v -> {
    FragmentManager manager = getParentFragment().getFragmentManager();
UploadSongFragment USF= new UploadSongFragment();
    manager.beginTransaction().replace(R.id.fragment_container,USF,"UploadFrag").addToBackStack(null).commit();
});
        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.post_getAll(session.getUniqueId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    System.out.println(s);
if(!s.equals("No data found")) {
    final JSONArray posts_data = new JSONArray(s);
    final int n = posts_data.length();
    if (n != 0) {
        User poster;
        Album album_p;
        Song song_p;
        ArrayList<Post> news = new ArrayList<>();
        ListView news_lv;
        PostAdapter PA;
        news_lv = myView.findViewById(R.id.news_lv);
        for (int i = 0; i < n; ++i) {
            JSONObject post = posts_data.getJSONObject(i);
            poster = new User(post.getString("unique_id"), post.getString("name"), post.getString("email"), post.getInt("role"));
            song_p = new Song(post.getInt("song_id"), post.getString("title"), poster, post.getString("song_mood"), post.getString("song_category"));
            news.add(new Post(post.getInt("id"),poster, song_p, post.getString("created_at")));
            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
        }
        PA = new PostAdapter(Objects.requireNonNull(getContext()), R.id.news_lv, news);
        news_lv.setAdapter(PA);

    }
}
                }));

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
