package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_profile,container,false);
        final FragmentManager ft= getFragmentManager();
        TextView txtProfilePost= myView.findViewById(R.id.txtPostsProfile);
        TextView txtProfileFollowers= myView.findViewById(R.id.txtFollowersProfile);
        TextView txtProfileFollowing= myView.findViewById(R.id.txtFollowingProfile);
        final TextView txtCountPosts = myView.findViewById(R.id.txtProfileCountPosts);
        final TextView txtCountFollowers = myView.findViewById(R.id.txtProfileCountFollowers);
        final TextView txtCountFollowing = myView.findViewById(R.id.txtProfileCountFollowing);
        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();

        //count posts
        CD.add(myAPI.getCountUserPosts("e5a618f9-6c21-419d-822b-5c093b037c01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray followers_data = new JSONArray(s);
                        final int n = followers_data.length();

                        for (int i = 0; i < n; ++i) {
                            JSONObject follower=followers_data.getJSONObject(i);
                            txtCountPosts.setText(follower.getString("res"));
                            System.out.println("name="+follower.getString("res"));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        //count followers
        CD.add(myAPI.getCountUserFollowers("e5a618f9-6c21-419d-822b-5c093b037c01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray followers_data = new JSONArray(s);
                        final int n = followers_data.length();

                        for (int i = 0; i < n; ++i) {
                            JSONObject follower=followers_data.getJSONObject(i);
                            txtCountFollowers.setText(follower.getString("res"));
                            System.out.println("name="+follower.getString("res"));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        //count followed
        CD.add(myAPI.getCountUserFollowed("e5a618f9-6c21-419d-822b-5c093b037c01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray followers_data = new JSONArray(s);
                        final int n = followers_data.length();

                        for (int i = 0; i < n; ++i) {
                            JSONObject follower=followers_data.getJSONObject(i);
                            txtCountFollowing.setText(follower.getString("res"));
                            System.out.println("name="+follower.getString("res"));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        txtProfilePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.beginTransaction().replace(R.id.PostsFragment, new ProfilePostsFragment()).commit();
            }
        });

        txtProfileFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.beginTransaction().replace(R.id.PostsFragment, new ProfileFollowersFragment()).commit();
            }
        });

        txtProfileFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.beginTransaction().replace(R.id.PostsFragment, new ProfileFollowingFragment()).commit();
            }
        });

        return myView;
    }
}
