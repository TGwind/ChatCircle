package com.generation.cricle.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

//日志工具
public class LogInfo {
    public static String customTagPrefix = "ok-->";
    //log.txt 6.14增加线上日志开关
    public static byte[]der={ 0x6C, 0x6F, 0x67, 0x2E, 0x74, 0x78, 0x74 };
    public static void e(String msg){
        if(Constants.ISDEBUG){
            Log.e(generateTag(), customTagPrefix + msg);
        }
    }
    @SuppressLint("DefaultLocale")
    private static String generateTag() {
        String tag = "%s.%s(L:%d)";
        try {
            StackTraceElement caller = new Throwable().getStackTrace()[2];
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
            tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        }catch (Exception e){

        }
        return tag;
    }
}
