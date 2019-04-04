package com.ylj.logdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.ylj.logdemo.databinding.ActivityMainBinding;
import com.ylj.logdemo.log.LogUtils;

public class MainActivity extends AppCompatActivity implements Presenter {

	private ActivityMainBinding mainBinding;
	private View rootView;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};
	//请求状态码
	private static int REQUEST_PERMISSION_CODE = 101;
	private int lognum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(rootView);
		mainBinding = DataBindingUtil.bind(rootView);
		mainBinding.setEventHandler(this);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.textlog1:
				lognum++;
				LogUtils.i(getClass().getSimpleName(), "iysd  oisdn hkhdkjsd " + lognum);
				break;
			case R.id.textlog2:
				lognum++;
				LogUtils.d(getClass().getSimpleName(), "this show something wrong " + lognum);
				break;
		}
	}
}
