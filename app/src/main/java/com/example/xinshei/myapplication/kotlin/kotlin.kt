package com.example.xinshei.myapplication.kotlin

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import com.example.xinshei.myapplication.R
import com.example.xinshei.myapplication.WaveView
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class kotlin : AppCompatActivity() {

//    private val layout: LinearLayout
//        get() {
//            val layout = findViewById(R.id.layout) as LinearLayout
//            return layout
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val array = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, array, 1)
        }

//        val toast = findViewById(R.id.toast) as Button
        toast.setOnClickListener {
            Toast.makeText(applicationContext, "showing toast", Toast.LENGTH_SHORT).show()
        }
        toast.alpha = 0.5f
        println("yes")
        val tv = TextView(this)
        tv.setTextColor(Color.RED)
        // this is comment
        /**
         * comment as well
         */
        tv.setText(getString(R.string.fk))
        val waveview = WaveView(applicationContext) as WaveView
        waveview.setBorder(10, Color.BLACK)
        layout.addView(waveview)

        layout.addView(tv)
        println(method(1, 2))
        println(lol())

        listall()


//        val asd = findViewById(R.id.asd) as Button
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
                    .setPositiveButton("asd", { dialog, _ -> dialog.dismiss() })
                    .setNegativeButton("negative", { _, a -> Log.e("asd", "ads" + a) })
                    .setCancelable(false)
                    .setTitle("this is title")
                    .show()

//            dialog.setContentView(tv2)
        }
        val lol = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, displayMetrics)
        something.setOnClickListener {

            doAsync {

                uiThread {
                    Toast.makeText(this@kotlin, "asd", Toast.LENGTH_LONG).show()
                    something.text = "??? ${asd.text} $lol"

                }
            }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //success
        } else {
            //false
        }
    }

}
