package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotificationsFragment extends Fragment {
    View view;

    public NotificationsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.notificationfragment,container,false);
        System.out.println("notiffragment");
        TextView txt =view.findViewById(R.id.txtNotifications);
        txt.setText("hello");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
