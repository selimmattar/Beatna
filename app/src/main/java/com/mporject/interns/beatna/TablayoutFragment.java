package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

public class TablayoutFragment extends Fragment {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView= inflater.inflate(R.layout.tablayoutfragment,container,false);
        tabLayout=myView.findViewById(R.id.tablayoutMenu);
        //appBarLayout=myView.findViewById(R.id.appbarMenu);
        viewPager=myView.findViewById(R.id.viewPagerMenu);
        MenuViewPagerAdapter adapter= new MenuViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new HomeFragment(),"Menu");
        adapter.addFragment(new DiscussFragment(),"Discuss");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return myView;
    }

}
