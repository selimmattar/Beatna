package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForumFragment extends Fragment {
    TextView txtUserName, txtDateDiscussion, txtSubjectDiscussion, txtContentDiscussion;
    ListView lv_discuss;
    EditText et_addComment;
    List<Comments> listCommentaires= new ArrayList<>();
    ImageButton imgBtnAddComment;
    Session userSession;
    CommentsAdapter cmtsAdapter;
    public ForumFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.discussion_item, container, false);
        txtUserName=myView.findViewById(R.id.txtUserDiscussItem);
        txtDateDiscussion=myView.findViewById(R.id.txtCreatedDiscussItem);
        txtSubjectDiscussion=myView.findViewById(R.id.txtSubjectDiscussItem);
        txtContentDiscussion= myView.findViewById(R.id.txtOpinionDiscussItem);
        lv_discuss=myView.findViewById(R.id.lvDiscussItem);
        imgBtnAddComment=myView.findViewById(R.id.imgBtnAddComment);
        et_addComment=myView.findViewById(R.id.etAddComment);
        Date currentTime = Calendar.getInstance().getTime();
        //System.out.println("argument= "+getArguments().getString("userName"));
        txtUserName.setText(getArguments().getString("userName"));
        txtDateDiscussion.setText(getArguments().getString("createdAt"));
        txtSubjectDiscussion.setText(getArguments().getString("subject"));
        txtContentDiscussion.setText(getArguments().getString("content"));
        userSession= new Session(getContext());
        NodeJS myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        CompositeDisposable CD = new CompositeDisposable();
        CD.add(myAPI.getCommentsByDiscussion(getArguments().getInt("idDiscuss"))
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
                            listCommentaires.add(new Comments(post.getString("username"),post.getString("createdAt"),post.getString("content")));
                             cmtsAdapter=new CommentsAdapter(getContext(), R.layout.lv_discuss_item,listCommentaires);
                            lv_discuss.setAdapter(cmtsAdapter);
                        }
                    }
                }));

        imgBtnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CD.add(myAPI.addComment(getArguments().getInt("idDiscuss"), userSession.getUniqueId(), et_addComment.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                                       @Override
                                       public void accept(String s) throws Exception {
                                           listCommentaires.add(new Comments(userSession.getUserName(),currentTime.toString(),et_addComment.getText().toString()));
                                           cmtsAdapter=new CommentsAdapter(getContext(),R.layout.lv_discuss_item,listCommentaires);
                                           lv_discuss.setAdapter(cmtsAdapter);
                                           ((BaseAdapter) cmtsAdapter).notifyDataSetChanged();

                                           Toast.makeText(getContext(),"Commented",Toast.LENGTH_SHORT).show();
                                       }
                                   }
                        ));
            }});
        return myView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getActivity().getWindow();
        w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
