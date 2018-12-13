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
