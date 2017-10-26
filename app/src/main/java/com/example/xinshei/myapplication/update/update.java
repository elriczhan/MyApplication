package com.example.xinshei.myapplication.update;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.xinshei.myapplication.RxManager;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by xinshei on 2017/10/11.
 */

public class update {
    private Handler mHandler = new Handler() {
        public void handleMessage(final Message msg) {
            Log.e("asd", msg.what + " percent!!");
            super.handleMessage(msg);


        }
    };

    public void check(final Context context) {
        Log.e("asd", "??????");
        RxManager.Instance().create(IUpdate.class).getUpdateInfo()
                .compose(RxManager.<Response<UpdateBean>>RunOnMainThread())
                .subscribe(new Observer<Response<UpdateBean>>() {
                    @Override
                    public void onComplete() {
                        Log.e("asd", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("asd", "error  " + e.toString());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<UpdateBean> response) {
                        if (response.code() == 200) {
                            download(context, response.body());
                            Log.e("asd", "start download");
                        } else {
                            Log.e("asd", "url error");
                        }
                    }
                });

    }

    public void download(Context context, UpdateBean bean) {

        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse("http://xinshui-android.oss-cn-beijing.aliyuncs.com/xinshui_android.apk"));
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        req.setDescription(bean.getDescription());
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setVisibleInDownloadsUi(true);
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, bean.getName() + bean.getVersion() + ".apk");
        req.setTitle(bean.getName() + bean.getVersion());
        req.setMimeType("application/vnd.android.package-archive");
        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        long downId = dm.enqueue(req);
        DownloadObserver downloadObserver = new DownloadObserver(mHandler, context, downId);
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/"),
                true,
                downloadObserver);
    }

    interface IUpdate {
        @GET("api/base/update/")
        Observable<Response<UpdateBean>> getUpdateInfo();
    }

    public class DownloadObserver extends ContentObserver {
        private Handler mHandler;
        private Context mContext;
        private int progress;
        private DownloadManager mDownloadManager;
        private DownloadManager.Query query;
        private Cursor cursor;

        public DownloadObserver(Handler handler, Context context, long downId) {
            super(handler);
            this.mHandler = handler;
            this.mContext = context;
            mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            query = new DownloadManager.Query().setFilterById(downId);
        }

        @Override
        public void onChange(boolean selfChange) {
            // 每当/data/data/com.android.providers.download/database/database.db变化后，触发onCHANGE，开始具体查询
            super.onChange(selfChange);
            //
            Log.e("asd", "onChange run!~~~~~");
            boolean downloading = true;
            while (downloading) {
                cursor = mDownloadManager.query(query);
                cursor.moveToFirst();
                int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                progress = (int) ((bytes_downloaded * 100) / bytes_total);
                mHandler.sendEmptyMessageDelayed(progress, 0);
//                Log.e("asd", progress + "--");
                if (cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false;
                }
                cursor.close();
            }
        }
    }
}
