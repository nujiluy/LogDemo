package com.ylj.logdemo;

import android.app.Application;

import com.ylj.logdemo.log.LogWriter;

/**
 * @author yulijun
 * @package com.ylj.logdemo
 * @date 2019/4/4 0004
 * @describe TODO
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LogWriter.Init();
	}
}
