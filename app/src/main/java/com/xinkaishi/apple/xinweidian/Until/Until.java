package com.xinkaishi.apple.xinweidian.Until;

import android.view.Menu;
import android.view.Window;

import java.lang.reflect.Method;

/**
 * Created by apple on 15/5/19.
 */
public class Until {
    /**
     * 利用反射让隐藏在Overflow中的MenuItem显示Icon图标
     * @param featureId
     * @param menu
     * onMenuOpened方法中调用
     */
    public void setOverflowIconVisiable(int featureId, Menu menu)
    {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }

    }
}
