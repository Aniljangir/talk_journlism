package com.talk.jounlist.talk_jurnlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Arun on 6/27/2016.
 */
public class Workshop extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.workshops, container, false);

        //tv_error=(TextView) rootView.findViewById(R.id.tv_error);

        return rootView;
    }
}
