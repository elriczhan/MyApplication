package com.example.xinshei.myapplication

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.helf.*

class StatusBarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, fragment4)
        return inflater?.inflate(R.layout.helf, container, false)
    }

}