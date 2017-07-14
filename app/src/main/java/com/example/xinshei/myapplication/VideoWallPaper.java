package com.example.xinshei.myapplication;

/**
 * Created by xinshei on 17/7/14.
 */


import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import java.io.IOException;

import static com.example.xinshei.myapplication.Constant.ACTION_VOICE_NORMAL;
import static com.example.xinshei.myapplication.Constant.ACTION_VOICE_SILENCE;


public class VideoWallPaper extends WallpaperService {
    private static final String TAG = VideoWallPaper.class.getName();
    private static String sVideoPath;

    /**
     * 设置静音
     *
     * @param context
     */
    public static void setVoiceSilence(Context context) {
        Intent intent = new Intent(Constant.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(Constant.ACTION, ACTION_VOICE_SILENCE);
        context.sendBroadcast(intent);
    }

    /**
     * 设置有声音
     *
     * @param context
     */
    public static void setVoiceNormal(Context context) {
        Intent intent = new Intent(Constant.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(Constant.ACTION, ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    /**
     * 设置壁纸
     *
     * @param context
     */
    public void setToWallPaper(Context context, String videoPath) {
        try {
            context.clearWallpaper();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sVideoPath = videoPath;
        SharedPreferences sp = context.getSharedPreferences("asd", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("path", sVideoPath);
        edit.apply();
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(context, VideoWallPaper.class));
        context.startActivity(intent);
    }


    @Override
    public Engine onCreateEngine() {
        return new VideoWallpagerEngine();
    }

    class VideoWallpagerEngine extends Engine {
        private MediaPlayer mMediaPlayer;
        private BroadcastReceiver mVideoVoiceControlReceiver;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            IntentFilter intentFilter = new IntentFilter(Constant.VIDEO_PARAMS_CONTROL_ACTION);
            mVideoVoiceControlReceiver = new VideoVoiceControlReceiver();
            registerReceiver(mVideoVoiceControlReceiver, intentFilter);
        }

        @Override
        public void onDestroy() {
            unregisterReceiver(mVideoVoiceControlReceiver);
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            SharedPreferences sp = app.app.getSharedPreferences("asd", MODE_PRIVATE);
            sVideoPath = sp.getString("path", "");
            if (TextUtils.isEmpty(sVideoPath)) {
                throw new NullPointerException("videoPath must not be null ");
            } else {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setSurface(holder.getSurface());

                try {

                    mMediaPlayer.setDataSource(sVideoPath);
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.setVolume(0f, 0f);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }

        class VideoVoiceControlReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                int action = intent.getIntExtra(Constant.ACTION, -1);
                switch (action) {
                    case ACTION_VOICE_NORMAL:
                        mMediaPlayer.setVolume(1.0f, 1.0f);
                        break;
                    case ACTION_VOICE_SILENCE:
                        mMediaPlayer.setVolume(0, 0);
                        break;
                }
            }
        }
    }


}
