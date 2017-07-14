package com.example.xinshei.myapplication.kotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.xinshei.myapplication.R

class kotlin : AppCompatActivity() {

    private val layout: LinearLayout
        get() {
            val layout = findViewById(R.id.layout) as LinearLayout
            return layout
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val toast = findViewById(R.id.toast) as Button
        toast.setOnClickListener {
            Toast.makeText(applicationContext, "showing toast", Toast.LENGTH_SHORT).show()
        }
        toast.alpha = 0.5f
        if (toast is Button) {
            println("yes")
        } else {
            println("no")
        }
        val tv = TextView(this)
        tv.setTextColor(Color.RED)
        // this is comment
        /**
         * comment as well
         */
        tv.setText(getString(R.string.fk))
//        val waveview = WaveView(applicationContext) as WaveView
//        waveview.setBorder(10,Color.BLACK)
//        layout.addView(waveview)
        layout.addView(tv)
        println(method(1, 2))
        println(lol())

        listall()


        val asd = findViewById(R.id.asd) as Button
        asd.setOnClickListener {
            val tv2 = TextView(this)
            tv2.setTextColor(Color.RED)
            // this is comment
            /**
             * comment as well
             */
            tv2.setText(getString(R.string.fk))

            AlertDialog.Builder(this)
                    .setView(tv2)
                    .setPositiveButton("asd", { dialog, which -> dialog.dismiss() })
                    .setNegativeButton("negative", { d, a -> Log.e("asd", "ads" + a) })
                    .setCancelable(false)
                    .setTitle("this is title")
                    .show()

//            dialog.setContentView(tv2)
        }
    }

    private fun listall() {
//        val list = listof("a", "b", "c")
//        for(a in list)
//        {
//            kotlin.io.println(a)
//        }
        for (a in 1..10) {
            println(" is &a " + (a + a) + a)
        }

        val list = listOf("apple", "banana", "kiwi")
        for (a in list.indices) {
            println("in position $a is ${list[a]}")

            when (list[a]) {
                "apple" -> println("this is $a ${list[a]} apple")
                else -> println("something else")
            }
        }
    }

    fun lol(): String {

        return "asd"
    }

    private fun method(a: Int, b: Int): String {
        return (a + b).toString()
    }

    override fun onStart() {
        super.onStart()
        System.out.println("start")
        Log.e("start", "start")
    }

    override fun onResume() {
        super.onResume()
        System.out.println("resume")
        Log.e("resume", "resume")


    }

    override fun onPause() {
        super.onPause()
        System.out.println("pause")
        Log.e("pause", "pause")

    }

    override fun onStop() {
        super.onStop()
        System.out.println("stop")
        Log.e("stop", "stop")


    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("destroy")
        Log.e("destroy", "destroy")

    }

}
