package com.example.xinshei.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by xinshei on 17/4/18.
 */

public class fragmentActivity extends Activity {

    private HelpFragment helpFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        helpFragment = new HelpFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment2, helpFragment).commit();

    }

    public Fragment getHelpFramgnt()
    {
        return  helpFragment;
    }
}
