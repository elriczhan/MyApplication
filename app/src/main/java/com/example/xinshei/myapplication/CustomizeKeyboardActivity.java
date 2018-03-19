package com.example.xinshei.myapplication;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * Created by xinshei on 2018/3/19.
 */

public class CustomizeKeyboardActivity extends AppCompatActivity {

    private KeyboardView keyboardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_keyboard);

        Keyboard keyboard = new Keyboard(this, R.xml.keyboard);

        keyboardView = findViewById(R.id.kbv_keyboard);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(true);

        final EditText ed = findViewById(R.id.et_cus);
        ed.setInputType(InputType.TYPE_NULL);
        ed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                keyboardView.setEnabled(true);
//                keyboardView.setPreviewEnabled(true);
                keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
                    @Override
                    public void onPress(int primaryCode) {

                    }

                    @Override
                    public void onRelease(int primaryCode) {

                    }

                    @Override
                    public void onKey(int primaryCode, int[] keyCodes) {
                        Editable editable = ed.getText();
                        int start = ed.getSelectionStart();
                        if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                            hideKeyboard();
                        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                            if (editable != null && editable.length() > 0) {
                                if (start > 0) {
                                    editable.delete(start - 1, start);
                                }
                            }
                        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
//                            changeKey();
//                            keyboardView.setKeyboard(k1);

                        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
//                            if (isnun) {
//                                isnun = false;
//                                keyboardView.setKeyboard(k1);
//                            } else {
//                                isnun = true;
//                                keyboardView.setKeyboard(k2);
//                            }
                        } else if (primaryCode == 57419) { // go left
                            if (start > 0) {
                                ed.setSelection(start - 1);
                            }
                        } else if (primaryCode == 57421) { // go right
                            if (start < ed.length()) {
                                ed.setSelection(start + 1);
                            }
                        } else {
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
                    }

                    @Override
                    public void onText(CharSequence text) {

                    }

                    @Override
                    public void swipeLeft() {

                    }

                    @Override
                    public void swipeRight() {

                    }

                    @Override
                    public void swipeDown() {

                    }

                    @Override
                    public void swipeUp() {

                    }
                });
                showKeyboard();
                return false;
            }
        });




    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str){
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase())>-1) {
            return true;
        }
        return false;
    }


}
