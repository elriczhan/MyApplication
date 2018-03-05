package com.elriczhan.basecore.base;

/**
 * Created by xinshei on 17/4/20.
 */

public interface BaseMVPView {
    void ShowLoadingView();

    void ShowErrorView(Exception e);

    void ShowSuccessView();
}
