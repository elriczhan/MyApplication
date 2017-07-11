package com.example.xinshei.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.xinshei.myapplication.kotlin.kotlin;
import com.example.xinshei.myapplication.mvp.LoginView;
import com.example.xinshei.myapplication.zxing.zxingActivity;
import com.umeng.message.PushAgent;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xinshei on 17/1/23.
 */

public class Menu extends AppCompatActivity implements View.OnClickListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Bind(R.id.edittext)
    EditText editText;

    @Bind(R.id.title)
    Toolbar tb;

    @Bind(R.id.image)
    ImageView image;
    private Button test3;
    private Button test6;

    @OnClick(R.id.swipe)
    void click() {
        startActivity(new Intent(this, Swipe.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulayout);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        Log.e("asd", "asd ---------");

        final Button test1 = (Button) findViewById(R.id.test1);
        Button test2 = (Button) findViewById(R.id.test2);
        test3 = (Button) findViewById(R.id.test3);
        Button test4 = (Button) findViewById(R.id.test4);
        Button test5 = (Button) findViewById(R.id.test5);
        test6 = (Button) findViewById(R.id.test6);
        Button button = (Button) findViewById(R.id.chart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ChartActivity.class));
            }
        });

        findViewById(R.id.fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, fragmentActivity.class));
            }
        });

        findViewById(R.id.indicator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, indicator.class));
            }
        });
        findViewById(R.id.mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, LoginView.class));
            }
        });
        findViewById(R.id.radarchart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, radarActivity.class));
            }
        });

        findViewById(R.id.radarchart2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, radarActivity2.class));
            }
        });

        findViewById(R.id.timeline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, TimeLine.class));
            }
        });

        findViewById(R.id.progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, progress.class));
            }
        });

        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, BaiduLoadingActivity.class));
            }
        });

        findViewById(R.id.zxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, zxingActivity.class));
            }
        });
        findViewById(R.id.error).setOnClickListener(new View.OnClickListener() {

            private PopupWindow mPopupWindow;

            @Override
            public void onClick(View v) {
                //make error
//                int i = 1 / 0;
                View inflate = View.inflate(Menu.this, R.layout.popup, null);
                inflate.findViewById(R.id.cancela).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPopupWindow != null && mPopupWindow.isShowing()) {
                            mPopupWindow.dismiss();
                        }
                    }
                });
                mPopupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopupWindow.showAtLocation(test1, Gravity.CENTER, 0, 0);

            }
        });

        findViewById(R.id.kotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, kotlin.class));
            }
        });

        findViewById(R.id.picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
                //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.set(2017, 5, 20);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2017, 5, 24);
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(Menu.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //选中事件回调
                        // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
//                        Button btn = (Button) v;
//                        btn.setText("asd");
                        Toast.makeText(Menu.this, "asd", Toast.LENGTH_SHORT).show();
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "点", "分", "")
                        .isCenterLabel(false)
                        .setDividerColor(Color.DKGRAY)
                        .setContentSize(21)
                        .setDate(selectedDate)
                        .setRangDate(startDate, endDate)
                        .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                        .setDecorView(null)
                        .build();
                pvTime.show();
            }
        });


        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        test3.setOnClickListener(this);
        test4.setOnClickListener(this);
        test5.setOnClickListener(this);
        test6.setOnClickListener(this);

        tb.setTitle("this is title");

        editText.setVisibility(View.INVISIBLE);

        RxManager.Instance().create(asd.class)
                .getStuff()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Response<String> stringResponse) {

                    }
                });
    }

    interface asd {
        @GET("tmms/appservices/user/")
        rx.Observable<Response<String>> getStuff();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test1:
                startActivity(new Intent(this, test1.class));
                break;
            case R.id.test2:
                startActivity(new Intent(this, DestroyAnimate.class));
                break;
            case R.id.test3:
                Pair<View, String> image1 = new Pair<View, String>(this.image, "image");
                Pair<View, String> button1 = new Pair<View, String>(this.test3, "button");
                Pair<View, String> button2 = new Pair<View, String>(this.test6, "button");
                //做这个效果 需要 这个view的 TransitionName 一样
                ActivityOptionsCompat img = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image1, button1, button2);
                startActivity(new Intent(this, shineButton.class), img.toBundle());
                break;
            case R.id.test4:
//                startActivity(new Intent(this, notification.class));
                editText.destroyDrawingCache();
                editText.buildDrawingCache();
                Bitmap bitmap = editText.getDrawingCache();
                Glide.with(this).load(R.mipmap.ic_launcher).placeholder(new BitmapDrawable(null, bitmap))
                        .bitmapTransform(new CropCircleTransformation(this)).into(image);
                break;
            case R.id.test5:
//                mDrawerLayout.openDrawer(Gravity.LEFT);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.test6:
                startActivity(new Intent(this, notificationA.class));
                break;

        }
    }

}
