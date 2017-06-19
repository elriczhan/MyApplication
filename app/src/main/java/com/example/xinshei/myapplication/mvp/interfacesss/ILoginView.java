package com.example.xinshei.myapplication.mvp.interfacesss;

import com.example.xinshei.myapplication.mvp.base.BaseMVPView;

/**
 * Created by xinshei on 17/4/20.
 */

public interface ILoginView extends BaseMVPView {
    String getUsername();

    String getPassword();

    void showResult(String result);
}
