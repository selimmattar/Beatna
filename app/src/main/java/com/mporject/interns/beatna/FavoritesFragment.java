package com.mporject.interns.beatna;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesFragment extends Fragment {

    ViewPager viewPager,viewPager2,viewPager3,viewPager4;
    TextView  txtFirstCategory,txtSecondCategory,txtThirdCategory;
    FavoritesPagerAdapter favoritesPagerAdapter;
    CategoriesPagerAdapter categoriesPagerAdapter;
    List<Model> models;
    List<Model> categoryModel;
    List<Model> favsModel;

    Integer[] colors = null;
    ArgbEvaluator argbEvaluator= new ArgbEvaluator();
    Boolean verif;
    String bestMood;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.fragment_favorites,container,false);

        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        DatabaseHelper myDb=new DatabaseHelper(getContext());
        String[] bestMoods={"","",""};
        String[] bestCategories={"","",""};
        String firstBestMood,secondBestMood,thirdBestMood;
        final String firstBestCategory,secondBestCategory,thirdBestCategory;
        Cursor resMood=myDb.getBest3Moods();
        Cursor resCategory=myDb.getBest3Categories();
        StringBuffer buffer=new StringBuffer();
        viewPager= myView.findViewById(R.id.viewPagerFavoris);
        viewPager2= myView.findViewById(R.id.viewPagerFavoris2);
        viewPager3= myView.findViewById(R.id.viewPagerFavoris3);
        txtFirstCategory=myView.findViewById(R.id.txtFirstCategoryForYou);
        while(resMood.moveToNext())
        {
            bestMoods[resMood.getPosition()]=resMood.getString(0);
        }
        while(resCategory.moveToNext())
        {
            bestCategories[resCategory.getPosition()]=resCategory.getString(0);
        }
        firstBestMood= bestMoods[0];
        secondBestMood=bestMoods[1];
        thirdBestMood=bestMoods[2];
        firstBestCategory=bestCategories[0];
        secondBestCategory=bestCategories[1];
        thirdBestCategory=bestCategories[2];
        System.out.println(firstBestMood);
        models= new ArrayList<>();
        favsModel= new ArrayList<>();
        categoryModel= new ArrayList<>();

//**************************************** ViewPager 2 (Recommended)
        CD.add(myAPI.getSongsThreeBestMoods(firstBestMood,5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept (String s) throws Exception {

                final JSONArray posts_data = new JSONArray(s);
                final int n = posts_data.length();

                for (int i = 0; i < n; ++i) {
                    JSONObject post = posts_data.getJSONObject(i);
                    models.add(new Model(R.drawable.relax, post.getString("title"), post.getString("artist")));
                }

                favoritesPagerAdapter= new FavoritesPagerAdapter(models,getContext());
                //First ViewPager
                viewPager.setAdapter(favoritesPagerAdapter);
                viewPager.setPadding(150, 0, 150, 0);
            }
        }));
        CD.add(myAPI.getSongsThreeBestMoods(secondBestMood,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String s) throws Exception {

                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();


                        for (int i = 0; i < n; ++i) {
                            JSONObject post = posts_data.getJSONObject(i);
                            models.add(new Model(R.drawable.relax, post.getString("title"), post.getString("artist")));
                        }
                        favoritesPagerAdapter= new FavoritesPagerAdapter(models,getContext());
                        //First ViewPager
                        viewPager.setAdapter(favoritesPagerAdapter);
                        viewPager.setPadding(150, 0, 150, 0);

                    }
                }));
        CD.add(myAPI.getSongsThreeBestMoods(thirdBestMood,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String s) throws Exception {

                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();


                        for (int i = 0; i < n; ++i) {
                            JSONObject post = posts_data.getJSONObject(i);
                            models.add(new Model(R.drawable.relax, post.getString("title"), post.getString("artist")));
                        }

                        favoritesPagerAdapter= new FavoritesPagerAdapter(models,getContext());
                        //First ViewPager

                        viewPager.setAdapter(favoritesPagerAdapter);
                        viewPager.setPadding(150, 0, 150, 0);

                    }
                }));
//***************************** ViewPager 1 (Favoris)
        CD.add(myAPI.getUserFavs("e5a618f9-6c21-419d-822b-5c093b037c01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String s) throws Exception {
                        txtFirstCategory.setText(firstBestCategory);
                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();

                        for (int i = 0; i < n; ++i) {
                            System.out.println("n="+n);
                            JSONObject post = posts_data.getJSONObject(i);
                            favsModel.add(new Model(R.drawable.happy, post.getString("title"), post.getString("artist")));
                        }
                        categoriesPagerAdapter= new CategoriesPagerAdapter(favsModel,getContext());
                        //Second ViewPager
                        viewPager2.setAdapter(categoriesPagerAdapter);
                        viewPager2.setPadding(150, 0, 150, 0);

                    }
                }));
//***************************** ViewPager 3 (Category for you)
       CD.add(myAPI.getSongsThreeBestCategories(firstBestCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept (String s) throws Exception {
                        txtFirstCategory.setText(firstBestCategory);
                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();

                        for (int i = 0; i < n; ++i) {
                            System.out.println("n="+n);
                            JSONObject post = posts_data.getJSONObject(i);
                            categoryModel.add(new Model(R.drawable.happy, post.getString("title"), post.getString("artist")));
                        }
                categoriesPagerAdapter= new CategoriesPagerAdapter(categoryModel,getContext());
                //Second ViewPager
                viewPager3.setAdapter(categoriesPagerAdapter);
                viewPager3.setPadding(150, 0, 150, 0);

                    }
                }));

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

        viewPager3.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position< (categoriesPagerAdapter.getCount() -1) && position <(colors.length -1))
                {
                    viewPager3.setBackgroundColor((Integer) argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                            )
                    );
                }else {
                    viewPager3.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
       // verif= saveInfoSharedPrefs();
       // bestMood=getDataSharedPrefs();
        //System.out.println("bestMood"+bestMood);
        return myView;
    }

   /* public boolean saveInfoSharedPrefs ()
    {
        SharedPreferences sharedPref = this.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString("bestMood", "");
        editor.putString("bestCategory","");
        editor.apply();
        return true;
    }

    public String getDataSharedPrefs()
    {
        SharedPreferences sharedPref = this.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String bestMood= sharedPref.getString("bestMood","");
        return bestMood;
    }
*/
}
