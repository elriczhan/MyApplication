package com.example.elric.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xinshei on 17/4/18.
 */

public class aFragment extends Fragment {
    private View rootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
//        getFragmentManager().beginTransaction().replace(R.id.fragment3, new HelpFragment()).commit();
//        Button viewById = (Button) rootView.findViewById(R.id.me);
//        viewById.setText("fragment a");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.af, null);
        // getScreenHW();
        return rootView;
    }
}
