package com.example.qbpan.helloworld;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;


import java.lang.Boolean;

/**
 * @author 作者 YYD
 * @version 创建时间：2016年12月14日 下午2:46:49
 * @function 未添加
 */
public class RobService extends AccessibilityService {
    /**
     * 此方法用来接收我的需要的各种事件 在accessibility.xml中我们监听了以下事件：
     * typeNotificationStateChanged typeWindowStateChanged
     * typeWindowContentChanged
     * http://www.jianshu.com/p/ba298b8d5a6e  看看这个是否被拆了判断
     */
    HashSet viewSet = new HashSet();
    LinkedList<String> link = new LinkedList<String>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            // 当通知栏发生改变的时候
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.v("serv1", "TYPE_NOTIFICATION_STATE_CHANGED");
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        if (content.contains(MatchNotification)) {
                            if ((event.getParcelableData() != null)
                                    && (event.getParcelableData() instanceof Notification)) {
                                Notification notification = (Notification) event
                                        .getParcelableData();
                                PendingIntent pendingIntent = notification.contentIntent;
                                try {
                                    pendingIntent.send();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                break;
            // 当窗口内容发生改变的时候
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.v("STATE_CHANGE","TYPE_WINDOW_CONTENT_CHANGED " + event.getClassName().toString() );
                String className1 = event.getClassName().toString();
                if (className1.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI0")||
                        className1.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI1") ||
                        className1.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI2")
                        ) {}; //聊天页
               {
                   //getLastPacket();
                    Log.v("STATE_CHANGE","Launcher dingxiang");
                    //inputTextClick("尝试登录");
                }


                break;
            // 当窗口状态发生改变的时候
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.v("STATE_CHANGE","TYPE_WINDOW_STATE_CHANGED " + event.getClassName().toString() );
                String className = event.getClassName().toString();

                if (className.equals("com.tencent.mm.ui.LauncherUI")) { //聊天页
                    Log.v("STATE_CHANGE","LauncherUI");
                    inputTextClick(MatchContentYuyin);
                }
                else if (className.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI0")||
                            className.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI1") ||
                            className.equals("com.tencent.mm.plugin.appbrand.ui.AppBrandUI2")
                        ) { //聊天页
                    //Log.v("STATE_CHANGE","Launcher dingxiang");
                    //inputTextClick("尝试登录");
                }

                break;
        }
    }

    /**
     * 播放系统默认提示音
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    /*
    public void defaultMediaPlayer() throws Exception {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(mContext, notification);
        r.play();
    }*/
    private void getLastPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        Log.v("STATE_CHANGE","getLastPacket");
        recycle(nodeInfo);

    }
    private void recycle(AccessibilityNodeInfo info) {
        Log.v("STATE_CHANGE","recycle");

        Log.v("STATE_CHANGE","for total:" + info.getChildCount());
        for (int i = 0; i < info.getChildCount(); i++) {
            Log.v("STATE_CHANGE","for :"+ i);
            if (info.getChild(i) != null) {

                recycle2(info.getChild(i),0);

            }
        }

    }
    private void recycle2(AccessibilityNodeInfo info,int layer) {
        Log.v("STATE_CHANGE", "recycle " + layer );
        Log.v("STATE_CHANGE", "info.getChildCount() : " + info.getChildCount()
                +" Class name :" + info.getClassName()
                + " text :"  + info.getText()
                + " res :"  + info.getViewIdResourceName()
                + " chick :"  + info.isClickable()
        );
        if(info.getClassName().equals("android.widget.LinearLayout" ) &&  info.isClickable())
        {
            //info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        if (info.getChildCount() !=0) {
            for (int i = 0; i < info.getChildCount(); i++)
                recycle2(info.getChild(i),layer + 1 );
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(RobService.this, "成功与微信绑定，开始监听", Toast.LENGTH_SHORT)
                .show();
    }
    /**
     * 根据id,获取AccessibilityNodeInfo，并点击。
     */

    private void inputTextClick(String text) {
        AccessibilityNodeInfo info = getRootInActiveWindow();
        Log.v("STATE_CHANGE","Action");
        if (info != null) {
            List<AccessibilityNodeInfo> list = info.findAccessibilityNodeInfosByText(text);
            if(list.size() !=0 ) {
                AccessibilityNodeInfo lastInfo = list.get(list.size() - 1);
                AccessibilityNodeInfo parent = lastInfo.getParent();
                Log.v("STATE_CHANGE", "Action: " + lastInfo.getClassName()
                        + " " + lastInfo.getParent().getClassName());
                if(parent != null
                        && parent.isClickable()
                        //&& (! viewSet.contains(lastInfo.getText().toString())))
                        && !(link.contains(lastInfo.getText().toString())))
                {
                    lastInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Log.v("STATE_CHANGE", "Action: " + lastInfo.getText().toString());
                    link.add(lastInfo.getText().toString());
                    if(link.size() > 3) {
                        link.pollFirst();
                    }
                }
            }
        }
    }

    String TextView       = "android.widget.TextView";
    String RelativeLayout = "android.widget.RelativeLayout";
 /*
    String MatchContentYuyin = "语音问诊";
    String MatchNotification = "来问丁香医生";
    String MatchCompareContent  ="发生时间";
    String CompareContent         ;
   */


    String MatchContentYuyin = "尝试登录";
    String MatchNotification = "潘强标";
  /* */







    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "断开与微信绑定，停止监听", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }
}