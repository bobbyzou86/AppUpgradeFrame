package cn.bobby.appupgrade;

import android.app.Application;
import android.util.Log;


/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author Bobby Zou(zouxiaobo@hisense.com) 2014-3-5.
 * @version $Id$
 */
public class AppUpgradeApplication extends Application {

    private AppUpgrade mUpgrade = null;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AppUpgrade", "onCreate");
        mUpgrade = new AppUpgrade(this.getApplicationContext());
        mUpgrade.checkVersion();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mUpgrade = null;
    }
    
    public void setOnNewVersionListener(IOnNewVersionListener listener,String appKey,String appSecret){
        mUpgrade.setOnNewVersionListener(listener,appKey,appSecret);
    }
}
