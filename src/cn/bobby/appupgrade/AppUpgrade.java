package cn.bobby.appupgrade;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
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
public class AppUpgrade {

    private static final String TAG = "AppUpgrade";
    private boolean TESTING = true;// TODO: test mode,should change to false;
	private Handler mHandler = new Handler();
    private IOnNewVersionListener mListener = null;
    private Context context = null;
    private String appKey = "";
    private String appSecret = "";
    private String token = "";
    private String packageName = "";
    
    
    public AppUpgrade(Context context){
    	this.context = context;
        packageName = context.getPackageName();
    }

    public synchronized void setOnNewVersionListener(IOnNewVersionListener listener,String appKey,String appSecret){
        this.mListener = listener;
        this.appKey = appKey;
    	this.appSecret = appSecret;
    	
        this.notifyAll();
    }
    
    public void upgradeApp(){
        Log.d(TAG, "upgradeApp");
        // if Application Store is preinstalled and your application is online
        // ,use code bellow:
        // TODO:To start Application Store as the Documentation。
    	
        // when No App Store is installed ,use function bellow:
    	upgradeAppByMyself();
    }
    
    /**
     * <p>
     * Title: upgrade apk not relay on App Store.
     * </p>
     * <p>
     * Description: we control the download progress by ourselves.
     * </p>
     * 
     */
	private void upgradeAppByMyself() {
        Log.d(TAG, "upgradeAppByMyself");
		UpgradeUIBuilder builder = new UpgradeUIBuilder(context,getAppUpgradeURL());
		builder.downloadNewApp();
	}

	private void doCheckVersion() {
        synchronized(this){
            if(mListener == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }   
        }
        final int oldVerCode = getOldVerCode(context);
        final int newVerCode = getnewVerCode();
        Log.d(TAG, "oldVerCode:" + oldVerCode + "^^newVerCode:" + newVerCode);
        mHandler.post(new Runnable() {
            public void run() {
                if (newVerCode > oldVerCode) {
                	final String oldVersion = getOldVerName(context);
                    final String newVersion = getnewVerName();
                    final String message = getnewVerDesc();
                    final int updateFlag = getUpdateFlag();
                    Log.d(TAG, "foundNewVersion:oldVersion:" + oldVersion + "^^newVersion:"
                            + newVersion + "^^updateFlag=" + updateFlag);
                    mListener.onNewVersion(oldVersion, newVersion, message, updateFlag,
                            AppUpgrade.this);
                } else {
                    Log.d(TAG, "not foundNewVersion");
                    mListener.onNoNewVersion();
                }
            }

        });
        
    }
   

	void checkVersion(){
        Log.d(TAG, "checkVersion");
        new AppUpgradeThread().start();
    }
    
    private class AppUpgradeThread extends Thread{
        
        public void run(){
            doCheckVersion();
        }
    }
    
    /**
	 * 获得当前应用的版本号
	 * @param context 
	 * @return 当前应用manifest文件中定义的versionCode值
	 */
	public int getOldVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
	   
	}

    /**
     * <p>
     * Title: get current version name defined in ANdroidManifest.xml.
     * </p>
     * <p>
     * Description: get current version name defined in ANdroidManifest.xml.It
     * is a String after android:versionName.
     * </p>
     * 
     * @param context
     * @return current version name.
     */
	public String getOldVerName(Context context) {
		String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
            		packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;   
	}

    /**
     * <p>
     * Title: get the newest version name from server.
     * </p>
     * <p>
     * Description: App should communicate with server to get the newest version
     * name.
     * </p>
     * 
     * @return newest version name.
     */
    private String getnewVerName() {
        if (TESTING) {
            // TODO: TESTING mode!You MUST get newest version name from server.
            return "Testing Version";
        }
		return null;
	}

    /**
     * <p>
     * Title: get the newest version code from server.
     * </p>
     * <p>
     * Description: App should communicate with server to get the newest version
     * code.
     * </p>
     * 
     * @return newest version code.
     */
    private int getnewVerCode() {
        if (TESTING) {
            // TODO: TESTING mode!You MUST get the newest code from server here.
            return 1000;
        }
        return 0;
	}

    /**
     * <p>
     * Title: get the newest version description from server.
     * </p>
     * <p>
     * Description: App should communicate with server to get the newest version
     * description which seems like a release note.
     * </p>
     * 
     * @return newest version code.
     */
    private String getnewVerDesc() {
        if (TESTING) {
            // TODO: TESTING mode!You MUST get the newest version description
            // from server here.
            return "This is a testing version!";
        }
        return null;
	}

    /**
     * <p>
     * Title: get the upgrade flag from server.
     * </p>
     * <p>
     * Description: 0--no need to upgrade;1--can cancel upgrade;2--must upgrade.
     * </p>
     * 
     * @return the upgrade flag.
     */
    private int getUpdateFlag() {
        if (TESTING) {
            // TODO: TESTING mode!You MUST get the flag from server here.
            return 2;
        }
        return 0;
    }

    /**
     * <p>
     * Title: get the URL for downloading newest version apk from server.
     * </p>
     * <p>
     * Description: to get the URL for downloading newest version apk.
     * </p>
     * 
     * @return URL String for downloading the newest apk.
     */
    private String getAppUpgradeURL() {
        if (TESTING) {
            // TODO: TESTING mode!You MUST get the URL for downloading newest
            // version apk.
            String url = "http://static.tieba.baidu.com/client/android/tiebaclient_5_6_1.apk?src=webtbGF";// bad
                                                                                                          // url
            return url;
        }
		return null;
	}
	
}
