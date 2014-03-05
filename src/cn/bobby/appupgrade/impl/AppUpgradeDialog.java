/* 
 * Copyright (C) 2011 Hisense Electric Co., Ltd. 
 * All Rights Reserved.
 *
 * ALL RIGHTS ARE RESERVED BY HISENSE ELECTRIC CO., LTD. ACCESS TO THIS
 * SOURCE CODE IS STRICTLY RESTRICTED UNDER CONTRACT. THIS CODE IS TO
 * BE KEPT STRICTLY CONFIDENTIAL.
 *
 * UNAUTHORIZED MODIFICATION OF THIS FILE WILL VOID YOUR SUPPORT CONTRACT
 * WITH HISENSE ELECTRIC CO., LTD. IF SUCH MODIFICATIONS ARE FOR THE PURPOSE
 * OF CIRCUMVENTING LICENSING LIMITATIONS, LEGAL ACTION MAY RESULT.
 */

package cn.bobby.appupgrade.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.bobby.appupgrade.R;

public class AppUpgradeDialog extends Dialog implements android.view.View.OnClickListener {

    private TextView current_version = null;
    private String current_version_s = "";
    private TextView newest_version = null;
    private String newest_version_s = "";
    private TextView update_content = null;
    private String update_content_s = "";
    private Button update = null;
    private Button cancel = null;
    private Context mContext = null;
    private TextView mUpdateRemind = null;
    private int mUpdateFlag = 0;// 0 无升级 1-普通升级 2-强制升级
    
    /**
     * <p>Title: TODO.</p>
     * <p>Description: TODO.</p>
     *
     * @param context
     * @param theme
     */
    public AppUpgradeDialog(Context context, int theme, String oldVersion, String newVersion,
            String content, int updateFlag) {
        super(context, theme);
        this.mContext = context;
        this.current_version_s = oldVersion;
        this.newest_version_s = newVersion;
        this.update_content_s = content;
        this.mUpdateFlag = updateFlag;
    }

    /**
     * <p>Title: TODO.</p>
     * <p>Description: TODO.</p>
     * 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i("AppUpgradeDialog", "AppUpgradeDialog:onCreate()");
        setContentView(R.layout.app_upgrade_dialog);
        
        Window w = getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wl = w.getAttributes();
        w.setAttributes(wl);
        
        current_version = (TextView) findViewById(R.id.app_current_version);
        current_version.setText(current_version_s);
        
        newest_version = (TextView) findViewById(R.id.app_newest_version);
        newest_version.setText(newest_version_s);
        
        update_content = (TextView) findViewById(R.id.update_content);
        update_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        update_content.setScrollbarFadingEnabled(false);
        update_content.setText(update_content_s);
        
        mUpdateRemind = (TextView) findViewById(R.id.update_remind);
        Log.i("AppUpgradeDialog", "AppUpgradeDialog:onCreate(),updateFlag=" + mUpdateFlag);
        if (mUpdateFlag == 2) {
            mUpdateRemind.setVisibility(View.VISIBLE);
        }

        update = (Button) findViewById(R.id.update);
        cancel = (Button) findViewById(R.id.cancel);
        
        update.requestFocus();
        
        update.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    /**
     * <p>Title: TODO.</p>
     * <p>Description: TODO.</p>
     * 
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.i("AppUpgradeDialog", "AppUpgradeDialog:onClick():"+v.getId());
        switch (v.getId()) {
            case R.id.cancel:
                this.dismiss();
                if (mUpdateFlag == 2) {
                    ((Activity) mContext).finish();
                    android.os.Process.killProcess(android.os.Process.myPid());// 需要杀掉自身进程，否则应用处于onStop状态，再次进入时，不会再次出发版本检查动作
                }
                break;
            case R.id.update:
                break;

            default:
                break;
        }
    }

    /**
     * 
     * <p>
     * Title: 返回键处理.
     * </p>
     * <p>
     * Description: 如果是强制升级，按返回键则退出应用.
     * </p>
     * 
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mUpdateFlag == 2) {
            this.dismiss();
            ((Activity) mContext).finish();
        }
    }

}
