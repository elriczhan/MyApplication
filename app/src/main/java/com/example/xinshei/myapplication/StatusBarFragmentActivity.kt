package com.example.xinshei.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class StatusBarFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_bar_fragment)

        fragmentManager.beginTransaction().replace(R.id.fl_fragment, StatusBarFragment()).commitAllowingStateLoss()

    }
}
