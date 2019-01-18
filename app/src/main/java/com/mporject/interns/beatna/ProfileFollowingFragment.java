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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileFollowingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.profile_followers_fragment,container,false);

        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.getUserFollowed(getArguments().getString("uniqueId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray followers_data = new JSONArray(s);
                        final int n = followers_data.length();
                        ArrayList<String> abonnements=new ArrayList<>();
                        ListView abos_lv;
                        FollowersAdapter FA;
                        abos_lv=myView.findViewById(R.id.userfollowers_lv);
                        for (int i = 0; i < n; ++i) {
                            JSONObject follower=followers_data.getJSONObject(i);
                            abonnements.add(follower.getString("name"));
                            System.out.println("name="+follower.getString("name"));
                            //   Toast.makeText(getContext(),post.getString("artist"),Toast.LENGTH_SHORT).show();
                        }
                        FA = new FollowersAdapter(getContext(),R.layout.follow_item,abonnements);
                        abos_lv.setAdapter(FA);

                    }
                }));

        return myView;
    }
}
