package com.example.xinshei.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.xinshei.myapplication.alertview.AlertView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {


    @OnClick(R.id.showedit)
    void click() {
//        Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Light_Dialog);
        EditText messageView = new EditText(this);
//        dialog.setContentView(et);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//        et.requestFocus();
//        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
//        }
        AlertView alertView = new AlertView(null, null, null, null, null, this, AlertView.Style.ActionNoMargin, null).setCancelable(true)
                .addExtView(messageView);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        alertView.show();
        messageView.requestFocus();
        if (imm != null) {
            imm.showSoftInput(messageView, InputMethodManager.SHOW_FORCED);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }
}
