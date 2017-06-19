package com.example.xinshei.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xinshei on 17/4/18.
 */

public class HelpFragment extends android.app.Fragment {

    private View rootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.fragment4, new otherFragment()).commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.helf, null);
        // getScreenHW();
        return rootView;
    }

    public void swtichit(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment4, fragment).commit();
    }

}
