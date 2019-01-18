package com.mporject.interns.beatna;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder>{
Context mContext;
List<ItemDiscuss> mData;

    public Adapter(Context mContext, List<ItemDiscuss> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View v= inflater.inflate(R.layout.forum_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int i) {
        holder.background_img.setImageResource(mData.get(i).getBackground());
        holder.profile_photo.setImageResource(mData.get(i).getProfilePhoto());
        holder.tv_title.setText(mData.get(i).getItemTitle());
        holder.tv_nbFollowers.setText(mData.get(i).getNbFollowers());
        holder.btnDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForumFragment forumFragment=new ForumFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("idDiscuss",mData.get(i).getId());
                bundle.putString("userName",mData.get(i).getItemTitle());
                bundle.putString("subject",mData.get(i).getNbFollowers());
                bundle.putString("content",mData.get(i).getContentDiscuss());
                bundle.putString("createdAt",mData.get(i).getDateDiscuss());
                forumFragment.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) mContext ).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,forumFragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_photo, background_img;
        TextView tv_title,tv_nbFollowers;
        Button btnDiscuss;

        public myViewHolder(View itemView)
        {
            super(itemView);
            profile_photo=itemView.findViewById(R.id.imgProfileDiscuss);
            background_img=itemView.findViewById(R.id.card_background);
            tv_title=itemView.findViewById(R.id.txtTitleDiscuss);
            tv_nbFollowers=itemView.findViewById(R.id.txtNbFollowersDiscuss);
            btnDiscuss=itemView.findViewById(R.id.btnFollowDiscuss);
        }
    }
}
