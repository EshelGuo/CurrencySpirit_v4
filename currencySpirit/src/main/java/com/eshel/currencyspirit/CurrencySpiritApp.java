package com.eshel.currencyspirit;

import android.content.ClipboardManager;
import android.content.Context;

import com.eshel.currencyspirit.util.UIUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import baseproject.base.BaseApplication;
import baseproject.util.Log;

/**
 * createBy Eshel
 * createTime: 2017/10/4 20:24
 * desc: TODO
 */

public class CurrencySpiritApp extends BaseApplication{
	private static CurrencySpiritApp app;
	private static String mainThreadName;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		mainThreadName = Thread.currentThread().getName();
		//开启信鸽日志输出
		XGPushConfig.enableDebug(this, true);
		//信鸽注册代码
		XGPushManager.registerPush(this, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.i("TPush", "注册成功，设备token为：" + data);
				ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(data.toString());
				UIUtil.toast("token 已经成功复制到剪切板 , 请使用 token 调试");
			}
			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.i("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
			}
		});
	}

	@Override
	public void onTerminate() {
		app = null;
		super.onTerminate();
	}

	public static String getMainThreadName(){
		return mainThreadName;
	}
	public static CurrencySpiritApp getApp() {
		return app;
	}
}
