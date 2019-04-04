package com.ylj.logdemo.log;

import android.util.Log;

/**
 * Created by yulijun on 2018/9/6 0006.
 */

public class LogUtils {
    private static final String TAG = "logdemo";
    private static boolean sOpenLog = true;
    private static boolean sWriteLog = true;


    public LogUtils() {
    }

    public static void setOpenLog(boolean openLog) {
    }

    public static void d(String logKey, String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(logKey).append("]").append(build(msg, ste));
            String log = sb.toString();
            Log.d(TAG, log);
            if (sWriteLog) LogWriter.printLog(log);
        }
    }

    public static void d(String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(msg, ste);
            Log.d(TAG, log);
            if (sWriteLog) LogWriter.printLog(log);
        }

    }

    public static void i(String logKey, String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(msg, ste);
            Log.i(TAG, "[" + logKey + "]" + log);
            if (sWriteLog) LogWriter.printLog(log);
        }

    }

    public static void v(String logKey, String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(msg, ste);
            short maxLength = 1000;
            if (log != null && log.length() > maxLength) {
                int beginIndex;
                for (beginIndex = 0; beginIndex + maxLength < log.length(); beginIndex += maxLength) {
                    Log.v(TAG, "[" + logKey + "]" + log.substring(beginIndex, beginIndex + maxLength));
                }
                Log.v(TAG, "[" + logKey + "]" + log.substring(beginIndex, log.length() - 1));
            } else {
                Log.v(TAG, "[" + logKey + "]" + log);
            }
            if (sWriteLog) LogWriter.printLog(log);
        }

    }

    public static void w(String logKey, String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(logKey, msg, ste);
            Log.w(TAG, log);
            if (sWriteLog) LogWriter.printLog(log);
        }

    }

    public static void e(String logKey, String msg) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(logKey, msg, ste);
            Log.e(TAG, log);
            if (sWriteLog) LogWriter.printLog(log);
        }
    }

    public static void e(String logKey, String msg, Throwable e) {
        if (sOpenLog) {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            String log = build(logKey, msg, ste);
            Log.e(TAG, log, e);
            if (sWriteLog) LogWriter.printLog(log);
        }
    }

    private static void writeToFile(String strLog) {
    }

    private static String build(String log, StackTraceElement ste) {
        StringBuilder buf = new StringBuilder();
        if (ste.isNativeMethod()) {
            buf.append("(Native Method)");
        } else {
            String fName = ste.getFileName();
            if (fName == null) {
                buf.append("(Unknown Source)");
            } else {
                int lineNum = ste.getLineNumber();
                buf.append('(');
                buf.append(fName);
                if (lineNum >= 0) {
                    buf.append(':');
                    buf.append(lineNum);
                }

                buf.append("):");
            }
        }

        buf.append(log);
        return buf.toString();
    }

    private static String build(String logKey, String msg, StackTraceElement ste) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(logKey).append("]").append(build(msg, ste));
        return sb.toString();
    }

    private static String build(String logKey, String msg, StackTraceElement ste, Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(logKey).append("]").append(ste.toString()).append(":").append(msg).append("\r\n").append("e:").append(e.getMessage());
        return sb.toString();
    }




 /*static {
       LogWriter.setLogImpl(new LogImpl() {
            public void printLog(int priority, String tag, String msg) {
                Log.d(tag, msg);
            }
        });
    }*/
}
