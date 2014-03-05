package cn.bobby.appupgrade.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.bobby.appupgrade.AppUpgradeApplication;
import cn.bobby.appupgrade.R;
import cn.bobby.appupgrade.impl.OnNewVersionListener;

/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Bobby Zou(zouxiaobo@hisense.com) 2014-3-5.
 * @version $Id$
 */

public class AppUpgradeTestActivity extends Activity {

    private BroadcastReceiver mUpgradeMsgReceiver = null;// AppUpgrade message
                                                         // BroadcastReceiver
    public static final String TAG = "AppUpgrade";
    // AppUpgrade broadcastReceiver actions
    public static final String ACTION_UPGRADE_DOWNLOAD_BEGIN = "com.appupgrade.downloadbegin";
    public static final String ACTION_UPGRADE_DOWNLOAD_SUCCESS = "com.appupgrade.downloadsuccess";
    public static final String ACTION_UPGRADE_NETWORK_DISCONNECTED = "com.appupgrade.networkdisconnected";
    public static final String ACTION_UPGRADE_IOEXCEPTION = "com.appupgrade.ioexception";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ((AppUpgradeApplication) getApplication()).setOnNewVersionListener(
                new OnNewVersionListener(this), "", "");

        if (mUpgradeMsgReceiver == null) {
            mUpgradeMsgReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.i(TAG, "mUpgradeMsgReceiver Action = " + intent.getAction());
                    if (intent.getAction().equals(ACTION_UPGRADE_DOWNLOAD_BEGIN)) {
                        Log.i(TAG, "mUpgradeMsgReceiver Action =1 " + intent.getAction());
                        makeToast(AppUpgradeTestActivity.this, R.drawable.upgrade_toast_media,
                                R.string.downloading_new_apk).show();
                    } else if (intent.getAction().equals(ACTION_UPGRADE_DOWNLOAD_SUCCESS)) {
                        Log.i(TAG, "mUpgradeMsgReceiver Action =2 " + intent.getAction());
                        makeToast(AppUpgradeTestActivity.this, R.drawable.upgrade_toast_media,
                                R.string.downloading_success).show();

                    } else if (intent.getAction().equals(ACTION_UPGRADE_NETWORK_DISCONNECTED)) {
                        Log.i(TAG, "mUpgradeMsgReceiver Action =3 " + intent.getAction());
                        makeToast(AppUpgradeTestActivity.this, R.drawable.upgrade_toast_warning,
                                R.string.downloading_terminated).show();

                    } else if (intent.getAction().equals(ACTION_UPGRADE_IOEXCEPTION)) {
                        Log.i(TAG, "mUpgradeMsgReceiver Action =4 " + intent.getAction());
                        makeToast(AppUpgradeTestActivity.this, R.drawable.upgrade_toast_warning,
                                R.string.downloading_terminated).show();

                    }
                }
            };
            IntentFilter upgradeMsgFilter = new IntentFilter();
            upgradeMsgFilter.addAction(ACTION_UPGRADE_DOWNLOAD_BEGIN);
            upgradeMsgFilter.addAction(ACTION_UPGRADE_DOWNLOAD_SUCCESS);
            upgradeMsgFilter.addAction(ACTION_UPGRADE_NETWORK_DISCONNECTED);
            upgradeMsgFilter.addAction(ACTION_UPGRADE_IOEXCEPTION);
            registerReceiver(mUpgradeMsgReceiver, upgradeMsgFilter);
        }
    }

    /**
     * <p>
     * Title: To make a Toast used specified ImageResource.
     * </p>
     * <p>
     * Description: To make a Toast used specified ImageResource.
     * </p>
     * 
     * @param context
     * @param imageResId
     * @param textResId
     * @return the required toast;
     */
    @SuppressLint("ShowToast")
    public static Toast makeToast(Context context, int imageResId, int textResId) {
        // 创建一个Toast提示消息
//        Toast toast = new Toast(context);
        Toast toast = Toast.makeText(context, textResId, Toast.LENGTH_LONG);
        // 设置Toast提示消息在屏幕上的位置
        // toast.setGravity(Gravity.CENTER, 0, 0);
        // 获取Toast提示消息里原有的View
        View toastView = toast.getView();
        // 创建一个ImageView
        ImageView img = new ImageView(context);
        img.setImageResource(imageResId);
        // 创建一个LineLayout容器
        LinearLayout ll = new LinearLayout(context);
        ll.setGravity(Gravity.CENTER);
        // 向LinearLayout中添加ImageView和Toast原有的View
        ll.addView(img);
        ll.addView(toastView);
        // 将LineLayout容器设置为toast的View
        toast.setView(ll);

        return toast;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mUpgradeMsgReceiver != null) {
            unregisterReceiver(mUpgradeMsgReceiver);
        }
    }

}
