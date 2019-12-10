package com.example.yooho.zerostart.tools;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.app.usage.UsageStatsManager.INTERVAL_MONTHLY;

public class MiscTools {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void getUsedAppInfo(Context context) {            // 获取最近使用的app信息
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(Calendar.DATE, 1);
        beginCal.set(Calendar.MONTH, 8);
        beginCal.set(Calendar.YEAR, 2019);


        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.DATE, 1);
        endCal.set(Calendar.MONTH, 11);
        endCal.set(Calendar.YEAR, 2019);
//
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
//        Log.e("SS", simpleDateFormat.format(beginCal.getTimeInMillis()) + "    " + simpleDateFormat.format(endCal.getTimeInMillis()));


        Calendar calendar=Calendar.getInstance();
        long endt = calendar.getTimeInMillis();//结束时间
        calendar.add(Calendar.MONTH, -1);//时间间隔为一个月
        long statt = calendar.getTimeInMillis();//开始时间
        Log.e("SS", simpleDateFormat.format(statt) + "    " + simpleDateFormat.format(endt));

        final UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);// Context.USAGE_STATS_SERVICE);
//        final List<UsageStats> queryUsageStats=usageStatsManager.queryUsageStats(INTERVAL_MONTHLY, statt, endt);
        final List<UsageStats> queryUsageStats=usageStatsManager.queryUsageStats(INTERVAL_MONTHLY, statt, endt);

        for (UsageStats app :queryUsageStats) {
            if (app.getLastTimeUsed() > 0) {

                Log.e("SS", simpleDateFormat.format(app.getLastTimeUsed()) + "    " + app.getPackageName() + "       " + simpleDateFormat.format(app.getTotalTimeInForeground()));
            }
        }
        Log.e("SS", queryUsageStats.size() + "");
    }

    public static void getPackagesForUid(Context context, int uid) {
        String[] info = context.getPackageManager().getPackagesForUid(uid);
        if (info == null) {
            Log.e("SS", uid + " no info");
        } else {
            for (String str : info) {
                Log.e("SS", str);
            }
        }
    }
}
