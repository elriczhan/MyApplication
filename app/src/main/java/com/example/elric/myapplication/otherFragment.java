package com.example.elric.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by xinshei on 17/4/18.
 */

public class otherFragment extends Fragment {
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
        Button viewById = (Button) rootView.findViewById(R.id.me);
        viewById.setText("other");
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("asd", "---------");
                HelpFragment helpFramgnt = (HelpFragment) ((fragmentActivity) otherFragment.this.getActivity()).getHelpFramgnt();
                helpFramgnt.swtichit(new aFragment());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.helpf, null);
        // getScreenHW();
        return rootView;
    }
}
