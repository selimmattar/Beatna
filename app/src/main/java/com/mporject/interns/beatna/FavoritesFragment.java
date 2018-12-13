package com.mporject.interns.beatna;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesFragment extends Fragment {

    ViewPager viewPager;
    FavoritesPagerAdapter favoritesPagerAdapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator= new ArgbEvaluator();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_favorites,container,false);

        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        models= new ArrayList<>();
        viewPager= myView.findViewById(R.id.viewPagerFavoris);

        CD.add(myAPI.getUserFavs("e5a618f9-6c21-419d-822b-5c093b037c01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {

                final JSONArray posts_data = new JSONArray(s);
                final int n = posts_data.length();

                for (int i = 0; i < n; ++i) {
                    JSONObject post = posts_data.getJSONObject(i);
                    models.add(new Model(R.drawable.brochure, post.getString("title"), post.getString("artist")));
                }
                favoritesPagerAdapter= new FavoritesPagerAdapter(models,getContext());
                viewPager.setAdapter(favoritesPagerAdapter);
                viewPager.setPadding(130,0,130,0);
            }

        }));

       /* models.add(new Model(R.drawable.brochure, "Brochure", "Selim And Emir Are The Best "));
        models.add(new Model(R.drawable.sticker, "Sticker", "Selim And Emir Are The Best"));
        models.add(new Model(R.drawable.poster, "Poster", "Selim And Emir Are The Best"));
        models.add(new Model(R.drawable.namecard, "Namecard", "Selim And Emir Are The Best"));

        favoritesPagerAdapter= new FavoritesPagerAdapter(models,this.getContext());

        viewPager= myView.findViewById(R.id.viewPagerFavoris);
        viewPager.setAdapter(favoritesPagerAdapter);
        viewPager.setPadding(130,0,130,0);
        */
        Integer[] colors_temp= {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
        };

        colors=colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position< (favoritesPagerAdapter.getCount() -1) && position <(colors.length -1))
                {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                            )
                    );
                }else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return myView;
    }
}
