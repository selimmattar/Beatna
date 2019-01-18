package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DiscussFragment extends Fragment {
    List<ItemDiscuss> mlist= new ArrayList<>();

    public DiscussFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View discussView=inflater.inflate(R.layout.discussfragment,container,false);

      /*  // set te statue bar background to transparent
        Window w = getActivity().getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        */
        RecyclerView recyclerView;
        recyclerView=discussView.findViewById(R.id.rv_list);

        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.getAllDiscussions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();
                        //System.out.println("data n +"+n);
                        for (int i = 0; i < n; ++i) {
                            JSONObject post = posts_data.getJSONObject(i);
                            int id =Integer.parseInt(post.getString("id"));
                            mlist.add(new ItemDiscuss(post.getString("createdAt"),post.getString("content"),id,R.drawable.astroworld,post.getString("username"),R.drawable.red_circle,post.getString("subject")));
                            Adapter adapter = new Adapter(getContext(), mlist);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }
                }));
        // set recycler view with the adapter


       // mlist.add(new ItemDiscuss(R.drawable.astroworld,"Astroworld",R.drawable.red_circle,2500));


        return discussView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
