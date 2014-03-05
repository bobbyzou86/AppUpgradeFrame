package cn.bobby.appupgrade.impl;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import cn.bobby.appupgrade.AppUpgrade;
import cn.bobby.appupgrade.IOnNewVersionListener;
import cn.bobby.appupgrade.R;

public class OnNewVersionListener implements IOnNewVersionListener {

    private Activity mCurrentActivity;

    public OnNewVersionListener(Activity activity) {
        mCurrentActivity = activity;
    }

    @Override
    public void onNoNewVersion() {
        // TODO Auto-generated method stub

    }

    public void onNewVersion(String oldVersion, String newVersion, String message,
            final int updateFlag, final AppUpgrade upgrade) {

        final AppUpgradeDialog dialog = new AppUpgradeDialog(mCurrentActivity,
                R.style.appupgrade_dialog, oldVersion, newVersion, message, updateFlag);
        dialog.show();
        dialog.findViewById(R.id.update).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                upgrade.upgradeApp();
            }
        });

    }

}
