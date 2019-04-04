package com.ylj.logdemo.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yulijun on 2018/9/6 0006.
 */

public class LogWriter {

	private static final int MAX_LOG_FILE_LEN = 104857600;
	private static final int MSGNUMBER = 1000;
	private static LogWriteHandler sLogWriteHandler = new LogWriteHandler("MyApplication");
	private static String logFileName = "log_"+new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".txt";
	private static String logFilePath = Environment.getExternalStorageDirectory().toString() + "/logdemo/logs/";
	private static String logFile = logFilePath+logFileName;

	public static void Init() {
		File pathFile = new File(logFilePath);
		File file = new File(logFile);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			/*if (file != null && file.length() > MAX_LOG_FILE_LEN) {
				file.delete();
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void relasePrint() {
		sLogWriteHandler.relase();
		sLogWriteHandler = null;
	}

	public static void printLog(String str) {
		Message msg = sLogWriteHandler.obtainMessage();
		msg.what = MSGNUMBER;
		msg.obj = str;
		msg.sendToTarget();
	}

	private static final class LogWriteHandler extends Handler {
		private FileWriter mFileWriter;
		private SimpleDateFormat mDateFormat;

		public LogWriteHandler(String name) {
			this(name, 1);
		}

		public LogWriteHandler(String name, int priority) {
			super(startHandlerThread(name, priority).getLooper());
			this.mFileWriter = null;
			this.mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
			// Log.i("LogWriter", "LogWriteHandler:mDateFormat===> " + mDateFormat.toString());
		}


		private void relase() {
			try {
				mFileWriter.close();
				mFileWriter = null;
				mDateFormat = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static HandlerThread startHandlerThread(final String name, final int priority) {
			final AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
			HandlerThread ht = new HandlerThread(name, priority) {
				protected void onLooperPrepared() {
					localAtomicBoolean.compareAndSet(false, true);
				}
			};
			ht.start();
			while (!localAtomicBoolean.get()) {
				;
			}
			return ht;
		}

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSGNUMBER:
					this.writeToFile((String) msg.obj);
				default:
			}
		}

		private void writeToFile(String strLog) {
			if (this.mFileWriter == null) {
				try {
					this.mFileWriter = new FileWriter(logFile, true);
				} catch (IOException var7) {
					Log.e("_LogDemo_", "create file writer fail", var7);
				}
			}
			try {
				this.mFileWriter.write(this.mDateFormat.format(new Date()));
				this.mFileWriter.write(":");
				this.mFileWriter.write(strLog);
				this.mFileWriter.write("\r\n");
				this.mFileWriter.flush();
			} catch (Exception var6) {
				Log.e("_LogDemo_", "write file fail", var6);
			}

		}
	}
}
