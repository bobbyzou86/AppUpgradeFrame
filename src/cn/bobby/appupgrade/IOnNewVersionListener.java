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

package cn.bobby.appupgrade;

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
public interface IOnNewVersionListener {

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param oldVersion
     *            : current version name define in AndroidManifest.xml.
     * @param newVersion
     *            : newest version name got from server.
     * @param message
     *            : upgrade content
     * @param updateFlage
     *            : if application can continue to be used before being upgraded
     * @param upgrade
     *            : AppUpgrade obj.
     */
    public void onNewVersion(String oldVersion, String newVersion, String message, int updateFlage,
            AppUpgrade upgrade);
    public void onNoNewVersion();
}
