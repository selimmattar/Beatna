package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AllProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_profile,container,false);

        Session userSession=new Session(getContext());
        System.out.println("uniqueid="+userSession.getUniqueId());
        final FragmentManager ft= getFragmentManager();
        TextView txtUserNameProfile= myView.findViewById(R.id.userNameProfile);
        TextView txtProfilePost= myView.findViewById(R.id.txtPostsProfile);
        TextView txtProfileFollowers= myView.findViewById(R.id.txtFollowersProfile);
        TextView txtProfileFollowing= myView.findViewById(R.id.txtFollowingProfile);
        Button btnFollowMe= myView.findViewById(R.id.btnFollowMeProfile);
        final TextView txtCountPosts = myView.findViewById(R.id.txtProfileCountPosts);
        final TextView txtCountFollowers = myView.findViewById(R.id.txtProfileCountFollowers);
        final TextView txtCountFollowing = myView.findViewById(R.id.txtProfileCountFollowing);
        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();

        txtUserNameProfile.setText(getArguments().getString("userName"));
        if (userSession.getUniqueId().equals(getArguments().getString("uniqueId")))
            btnFollowMe.setVisibility(View.INVISIBLE);
        //check abonnement
        CD.add(myAPI.checkAbonnement(userSession.getUniqueId(),getArguments().getString("uniqueId"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(!s.equals("Abonnement non valide"))
                        {
                            btnFollowMe.setText("Following");
                        }
                        else
                            btnFollowMe.setText("Follow Me");

                    }
                }));

        //Addd or Delete
        btnFollowMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFollowMe.getText().equals("Following"))
                {
                    CD.add(myAPI.deleteAbonnement(userSession.getUniqueId(),getArguments().getString("uniqueId"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                        btnFollowMe.setText("Follow Me");
                                        Toast.makeText(getContext(),"Unfollowed", android.widget.Toast.LENGTH_SHORT).show();
                                }
                            }));
                }
                else {
                    CD.add(myAPI.addAbonnement(userSession.getUniqueId(),getArguments().getString("uniqueId"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    btnFollowMe.setText("Following");
                                    Toast.makeText(getContext(),"Followed", android.widget.Toast.LENGTH_SHORT).show();
                                }
                            }));
                }
            }
        });
        //count posts
        CD.add(myAPI.getCountUserPosts(getArguments().getString("uniqueId"))
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
        CD.add(myAPI.getCountUserFollowers(getArguments().getString("uniqueId"))
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

        //count followed
        CD.add(myAPI.getCountUserFollowed(getArguments().getString("uniqueId"))
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

        txtProfilePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePostsFragment postsFragment=new ProfilePostsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uniqueId",getArguments().getString("uniqueId"));
                postsFragment.setArguments(bundle);
                ft.beginTransaction().replace(R.id.PostsFragment, postsFragment).commit();
            }
        });

        txtProfileFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFollowersFragment followersFragment=new ProfileFollowersFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uniqueId",getArguments().getString("uniqueId"));
                followersFragment.setArguments(bundle);
                ft.beginTransaction().replace(R.id.PostsFragment, followersFragment).commit();
            }
        });

        txtProfileFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFollowingFragment followingFragment=new ProfileFollowingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uniqueId",getArguments().getString("uniqueId"));
                followingFragment.setArguments(bundle);
                ft.beginTransaction().replace(R.id.PostsFragment, followingFragment).commit();
            }
        });
        return myView;
    }
}
