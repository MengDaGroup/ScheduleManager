package com.dayi35.qx_utils.common;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.text.TextUtils;


import com.dayi35.qx_utils.R;

import java.util.TimeZone;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2020/10/14 14:52
 * 描    述: 类 本地闹铃提醒插入工具 预留事件详情（注意：时间的单位是ms）
 * 调用示例：
 * requestNeedPermission(Manifest.permission.WRITE_CALENDAR, 100);     //权限请求
 * ReminderBuilder builder = new ReminderBuilder.Builder(SettingActivity.this, 1602662312000L, "测试时间4")
 * .build();
 * builder.insert();
 * 修订历史:
 * =========================================
 */
public class ReminderBuilder {
    private String calanderEventURL = "content://com.android.calendar/events";          //事件uri
    private Context mContext;               //上下文
    private Activity mActivity;
    private long mStart;                    //开始时间(ms)
    private long mEnd;                    //结束时间(ms)
    private String mTitle;                  //事件名
    private String mDetail;                 //事件详情
    private int beforeTime;                 //提前提醒

    private static String CALENDAR_NAME = "nk_name";
    private static String CALENDAR_ACCOUNT_NAME = "nk_account_name";
    private static String CALENDAR_DISPLAY_NAME = "nk_display_name";

    private ReminderBuilder(Builder builder) {
        this.mContext = builder.mContext;
        this.mStart = builder.mStart;
        this.mEnd = builder.mEnd;
        this.mTitle = builder.mTitle;
        this.mDetail = builder.mDetail;
        this.beforeTime = builder.beforeTime;
    }

    public static class Builder {
        private Context mContext;               //上下文
        private long mStart;                    //开始时间(ms)
        private String mTitle;                  //事件名
        private String mDetail;                 //事件详情
        private int beforeTime;                 //提前提醒
        private long mEnd = 0L;                  //结束时间(ms)

        public Builder(Context mContext, long mStart, String mTitle) {
            this.mContext = mContext;
            this.mStart = mStart;
            this.mTitle = mTitle;
        }

        public Builder eventDetail(String mDetail) {
            this.mDetail = mDetail;
            return this;
        }

        public Builder beforTime(int beforeTime) {
            this.beforeTime = beforeTime;
            return this;
        }

        public Builder endTime(long endTime) {
            this.mEnd = endTime;
            return this;
        }

        public ReminderBuilder build() {
            return new ReminderBuilder(this);
        }
    }

    /**
     * 插入事件
     *
     * @return
     */
    public boolean insert() {
        Uri uri2 = CalendarContract.Reminders.CONTENT_URI;

        ContentValues eValues = new ContentValues();                                //插入事件
        ContentValues rValues = new ContentValues();                                //插入提醒，与事件配合起来才有效
        TimeZone tz = TimeZone.getDefault();                                        //获取默认时区

        long calId = obtainCalendarAccountID(mContext);

        //插入日程
        eValues.put(CalendarContract.Events.DTSTART, mStart);
        eValues.put(CalendarContract.Events.DTEND, mEnd == 0L ? mStart : mEnd);
        eValues.put(CalendarContract.Events.TITLE, mTitle);
        eValues.put(CalendarContract.Events.DESCRIPTION, TextUtils.isEmpty(mDetail) ? "" : mDetail);
        eValues.put(CalendarContract.Events.CALENDAR_ID, calId);
        eValues.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());
        eValues.put(CalendarContract.Events.EVENT_LOCATION,
                "雀喜易购");

        Uri uri = mContext.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eValues);

        //插完日程之后必须再插入以下代码段才能实现提醒功能
        String myEventsId = uri.getLastPathSegment();                   // 得到当前表的_id
        rValues.put(CalendarContract.Reminders.EVENT_ID, myEventsId);                            //设置到事件ID
        rValues.put(CalendarContract.Reminders.MINUTES, beforeTime == 0 ? 10 : beforeTime);      //提前多少分钟提醒 默认10分钟
        rValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);                                       //如果需要有提醒,必须要有这一行
        mContext.getContentResolver().insert(uri2, rValues);
        return true;
    }

    /**
     * 日历事件删除（遍历 title）
     *
     * @param title
     * @return
     */
    public boolean delete(String title) {
        Cursor eventCursor = mContext.getContentResolver().query(Uri.parse(calanderEventURL), null, null, null, null);
        try {
            if (eventCursor == null) { //查询返回空值
                return false;
            }
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(calanderEventURL), id);
                        int rows = mContext.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) { //事件删除失败
                            return false;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
        return true;
    }

    public static long obtainCalendarAccountID(Context context) {
        long calID = checkCalendarAccount(context);
        if (calID >= 0) {
            return calID;
        } else {
            return createCalendarAccount(context);
        }
    }

    private static long checkCalendarAccount(Context context) {
        try (Cursor cursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                null, null, null, null)) {
            // 不存在日历账户
            if (null == cursor) {
                return -1;
            }
            int count = cursor.getCount();
            // 存在日历账户，获取第一个账户的ID
            if (count > 0) {
                cursor.moveToFirst();
                return cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        }
    }


    private static long createCalendarAccount(Context context) {
        // 系统日历表
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

        // 要创建的账户
        Uri accountUri;

        // 开始组装账户数据
        ContentValues account = new ContentValues();
        // 账户类型：本地
        // 在添加账户时，如果账户类型不存在系统中，则可能该新增记录会被标记为脏数据而被删除
        // 设置为ACCOUNT_TYPE_LOCAL可以保证在不存在账户类型时，该新增数据不会被删除
        account.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        // 日历在表中的名称
        account.put(CalendarContract.Calendars.NAME, CALENDAR_NAME);
        // 日历账户的名称
        account.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME);
        // 账户显示的名称
        account.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME);
        // 日历的颜色
        account.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.parseColor("#515bd4"));
        // 用户对此日历的获取使用权限等级
        account.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        // 设置此日历可见
        account.put(CalendarContract.Calendars.VISIBLE, 1);
        // 日历时区
        account.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().getID());
        // 可以修改日历时区
        account.put(CalendarContract.Calendars.CAN_MODIFY_TIME_ZONE, 1);
        // 同步此日历到设备上
        account.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        // 拥有者的账户
        account.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDAR_ACCOUNT_NAME);
        // 可以响应事件
        account.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 1);
        // 单个事件设置的最大的提醒数
        account.put(CalendarContract.Calendars.MAX_REMINDERS, 8);
        // 设置允许提醒的方式
        account.put(CalendarContract.Calendars.ALLOWED_REMINDERS, "0,1,2,3,4");
        // 设置日历支持的可用性类型
        account.put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "0,1,2");
        // 设置日历允许的出席者类型
        account.put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "0,1,2");

        /*
            TIP: 修改或添加ACCOUNT_NAME只能由SYNC_ADAPTER调用
            对uri设置CalendarContract.CALLER_IS_SYNCADAPTER为true,即标记当前操作为SYNC_ADAPTER操作
            在设置CalendarContract.CALLER_IS_SYNCADAPTER为true时,必须带上参数ACCOUNT_NAME和ACCOUNT_TYPE(任意)
         */
        uri = uri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDAR_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
                        CalendarContract.Calendars.CALENDAR_LOCATION)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查日历权限
            if (PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(
                    "android.permission.WRITE_CALENDAR")) {
                accountUri = context.getContentResolver().insert(uri, account);
            } else {
                return -2;
            }
        } else {
            accountUri = context.getContentResolver().insert(uri, account);
        }

        return accountUri == null ? -1 : ContentUris.parseId(accountUri);
    }

}
