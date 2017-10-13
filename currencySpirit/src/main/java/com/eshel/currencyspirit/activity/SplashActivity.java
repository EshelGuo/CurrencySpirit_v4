package com.eshel.currencyspirit.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.eshel.currencyspirit.CurrencySpiritApp;
import com.eshel.currencyspirit.R;
import com.eshel.currencyspirit.util.PermissionUtil;
import com.eshel.currencyspirit.util.UIUtil;

import baseproject.base.BaseActivity;
import baseproject.permission.RequestPermissionUtil;
import baseproject.util.Log;

/**
 * createBy Eshel
 * createTime: 2017/10/2 20:19
 * desc: 通用的 APP 欢迎界面
 */

public class SplashActivity extends BaseActivity {
	public final int ALL_TIME = 3000;
	public final int REQUEST_PERMISSION_TIME = 1500;
	public int lifeTime = ALL_TIME;
	private Runnable finishSplashTask = new Runnable() {
		@Override
		public void run() {
			enterHomeActivity();
		}
	};
	private Runnable mainTask = new Runnable() {
		@Override
		public void run() {
			updateNewVersion();
		}
	};

	private void updateNewVersion() {
		// TODO: 2017/10/4 检查更新
	}

	private void enterHomeActivity() {
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		getWindow().setBackgroundDrawableResource(R.drawable.splash);
		super.onCreate(savedInstanceState);
		CurrencySpiritApp.isExit = false;
		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null)
			actionBar.hide();
		lifeTime = REQUEST_PERMISSION_TIME;
		//		new Thread(mainTask).start();
		PermissionUtil.requestPermission(this, new PermissionUtil.PermissionCallback() {
			@Override
			public void requestAllPermissionSuccess() {
				CurrencySpiritApp.getApp().getHandler().postDelayed(finishSplashTask,lifeTime);
			}

			@Override
			public void hasAllPermission() {
				lifeTime = ALL_TIME;
				CurrencySpiritApp.getApp().getHandler().postDelayed(finishSplashTask,lifeTime);
			}
		},Manifest.permission.WRITE_EXTERNAL_STORAGE);

	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		RequestPermissionUtil.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
